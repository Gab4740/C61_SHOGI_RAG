package com.example.c61_shogi_rag.ui.screens.game_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.R
import com.example.c61_shogi_rag.engine.game.Game
import com.example.c61_shogi_rag.engine.piece.InitPiece
import com.example.c61_shogi_rag.engine.piece.PieceIDs
import com.example.c61_shogi_rag.engine.piece.Position
import com.example.c61_shogi_rag.engine.piece.ShogiPiece
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Charriot
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Chevalier
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Fou
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.GeneralArgent
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.GeneralOr
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Lance
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Pion


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

    fun parachutePiece(pieceCanonicalName: String) {
        var shogiPiece: ShogiPiece? = null

        when(pieceCanonicalName) {
            Pion::class.java.canonicalName -> shogiPiece = InitPiece.GetPion()
            Lance::class.java.canonicalName -> shogiPiece = InitPiece.GetLance()
            Chevalier::class.java.canonicalName -> shogiPiece = InitPiece.GetChevalier()
            GeneralArgent::class.java.canonicalName -> shogiPiece = InitPiece.GetGeneralArgent()
            GeneralOr::class.java.canonicalName -> shogiPiece = InitPiece.GetGeneralOr()
            Fou::class.java.canonicalName -> shogiPiece = InitPiece.GetFou()
            Charriot::class.java.canonicalName -> shogiPiece = InitPiece.GetCharriot()

        }
        game.gameBoard.setPieceAt(shogiPiece, Position(5,5))
    }
}