package com.example.c61_shogi_rag.ui.screens.game_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.game.Game
import com.example.c61_shogi_rag.engine.piece.Piece
import com.example.c61_shogi_rag.engine.piece.Position



class GameViewModel: ViewModel() {
    var game by mutableStateOf(Game(true))
        private set   // Permet d'accéder game à l'extérieur mais pas le modifier
    private var selectedPiece: Piece? = null
    private var selectedPosition: Position? = null
    private var destinationPosition: Position? = null

    init {
        game.GameInit()
    }
    fun selectPosition(position: Position) {
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
            }
        }
        game.prettyPrintConsoleBoard()
    }
}