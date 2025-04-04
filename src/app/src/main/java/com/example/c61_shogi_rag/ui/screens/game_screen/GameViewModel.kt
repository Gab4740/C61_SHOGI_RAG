package com.example.c61_shogi_rag.ui.screens.game_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.game.Game
import com.example.c61_shogi_rag.engine.piece.Position



class GameViewModel(_isPlayerFirst: Boolean): ViewModel() {
    var game by mutableStateOf(Game(_isPlayerFirst))
        private set   // Permet d'accéder game à l'extérieur mais pas le modifier
    var isPlayerTurn by mutableStateOf(game.isPlayerTurn)
    var counter by mutableIntStateOf(0)
    private var selectedPosition: Position? = null
    private var destinationPosition: Position? = null

    init {
        game.GameInit()
        if(!isPlayerTurn) {
            // Appeler l'ai
            isPlayerTurn = game.isPlayerTurn
        }
    }
    fun selectPosition(position: Position) {
        counter++
        println("${position.posX} ${position.posY}")
        if(game.isPlayerTurn) {
            if(game.isPlayerPieceAtPos(position)) {
                selectedPosition = position
            } else {
                destinationPosition = position
            }

            if(selectedPosition != null && destinationPosition != null) {
                game.playTurn(selectedPosition, destinationPosition)
                selectedPosition = null
                destinationPosition = null
                isPlayerTurn = game.isPlayerTurn // Force la récomposition
                // Appeler l'ai ici?
            }
        }
    }
}