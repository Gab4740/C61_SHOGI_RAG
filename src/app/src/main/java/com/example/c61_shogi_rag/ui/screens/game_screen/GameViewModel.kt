package com.example.c61_shogi_rag.ui.screens.game_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c61_shogi_rag.engine.dao.PartieDAO
import com.example.c61_shogi_rag.engine.entity.Partie
import com.example.c61_shogi_rag.engine.entity.PartieCallback
import com.example.c61_shogi_rag.engine.game.Game
import com.example.c61_shogi_rag.engine.piece.InitPiece
import com.example.c61_shogi_rag.engine.piece.Position
import com.example.c61_shogi_rag.engine.piece.ShogiPiece
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Charriot
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Chevalier
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Fou
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.GeneralArgent
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.GeneralOr
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Lance
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Pion
import com.google.gson.Gson
import kotlinx.coroutines.launch


class GameViewModel(isPlayerFirst: Boolean): ViewModel() {
    var game by mutableStateOf(Game(isPlayerFirst))
        private set   // Permet d'accéder game à l'extérieur mais pas le modifier
    var isPlayerTurn by mutableStateOf(game.isPlayerTurn)
    var counter by mutableIntStateOf(0)
    var isGameEnded by mutableStateOf(game.isGameEnded)
    private var selectedPosition: Position? = null
    private var selectedPieceToParchute: ShogiPiece? = null

    init {
        game.GameInit()
        if(!isPlayerTurn) {
            game.startMinimaxComputation();
            isPlayerTurn = game.isPlayerTurn
        }
    }
    fun selectPosition(position: Position) {
        if(game.isPlayerTurn) {
            playerTurn(position)
        }
    }
    private fun playerTurn(position: Position) {
        counter++
        viewModelScope.launch{
            if(!isGameEnded) {
                if(game.isPlayerPieceAtPos(position)) {
                    selectedPosition = position
                    selectedPieceToParchute = null
                } else if(selectedPosition != null) {
                    if(game.playTurn(selectedPosition, position)) {
                        isGameEnded = game.isGameEnded

                        aiTurn()
                    }
                    selectedPosition = null
                    isPlayerTurn = game.isPlayerTurn
                }
                else if(selectedPieceToParchute != null) {
                    if(game.parachuteWhitePiece(selectedPieceToParchute, position)) {
                        aiTurn()
                    }
                    selectedPieceToParchute = null
                }
            }
        }

    }
    private fun aiTurn() {
        counter++
        if(!isGameEnded) {
            game.aiTurn()
            isGameEnded = game.isGameEnded
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
        selectedPieceToParchute = shogiPiece

    }

    fun archiverPartie(id_gagnant: Int, id_perdant: Int) {
        if (isGameEnded) {
            try {
                val gson = Gson()

                val jsonString = gson.toJson(game.getGameSaver())

                PartieDAO.addPartie(id_gagnant, id_perdant, jsonString, object : PartieCallback {
                    override fun onPartiesRecuperees(partieList: List<Partie>) {
                        //voir pour peut etre mettre un toast
                    }

                    override fun onError(e: Exception) {
                        //voir pour peut etre mettre un toast
                    }
                })
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}