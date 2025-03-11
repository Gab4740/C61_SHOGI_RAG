package com.example.c61_shogi_rag.ui.screens.game_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.game.Game
import com.example.c61_shogi_rag.engine.piece.Piece
import com.example.c61_shogi_rag.engine.piece.Position

class GameViewModel: ViewModel() {
    //private var game: Game = Game(true)
    var game by mutableStateOf(Game(false))
        private set   // Permet d'accéder game à l'extérieur mais pas le modifier
    private var selectedPiece: Piece? = null

    init {
        game.GameInit()
    }
    fun selectPiece(position: Position) {
        selectedPiece = game.getPieceAt(position)
    }
}