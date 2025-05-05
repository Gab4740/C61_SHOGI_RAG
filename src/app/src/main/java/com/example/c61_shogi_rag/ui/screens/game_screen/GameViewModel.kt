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
import com.example.c61_shogi_rag.engine.minimax.Difficulty
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
import com.example.c61_shogi_rag.ui.screens.PlayerShareViewModel
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameViewModel(isPlayerFirst: Boolean, difficulty: Difficulty): ViewModel() {
    //var counter by mutableIntStateOf(0)
    val counter = mutableIntStateOf(0)
    var game by mutableStateOf(Game(isPlayerFirst, difficulty.strategy, counter))
        private set   // Permet d'accéder game à l'extérieur mais pas le modifier
    var isPlayerTurn by mutableStateOf(game.isPlayerTurn)
    var isGameEnded by mutableStateOf(game.isGameEnded)
    private var selectedPosition: Position? = null
    private var selectedPieceToParchute: ShogiPiece? = null
    var isPlayerFirst: Boolean = isPlayerFirst

    var playerID: Int = -1
    var opponentID: Int = 0
    private var isPlayerConnected: Boolean = false;

    var shouldPiecePromote by mutableStateOf(false)

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
        counter.value += 1
        if(!isGameEnded) {
            if(game.isPlayerPieceAtPos(position)) {
                selectedPosition = position
                selectedPieceToParchute = null
            } else if(selectedPosition != null) {
                if(game.playTurn(selectedPosition, position)) {
                    selectedPosition = null
                    isPlayerTurn = game.isPlayerTurn

                    isGameEnded = game.isGameEnded

                    aiTurn()


                }
                else {
                    selectedPosition = null
                    isPlayerTurn = game.isPlayerTurn
                }
            }
            else if(selectedPieceToParchute != null) {
                if(game.parachuteWhitePiece(selectedPieceToParchute, position)) {
                    aiTurn()
                }
                selectedPieceToParchute = null
            }
        }

    }
    private fun aiTurn() {
        if(!game.isGameEnded) {
            viewModelScope.launch {
                game.aiTurn()

                isGameEnded = game.isGameEnded
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
        selectedPieceToParchute = shogiPiece

    }

    fun setPlayerID(playerConnected:Boolean,id_joueur: Int, id_adversaire: Int) {
        if (playerConnected) {
            this.isPlayerConnected = playerConnected
            this.playerID = id_joueur
            /** si un multi decommenter la ligne suivante et faire a en sorte
             * d'aller chercher l'id du joueur adverse **/
            //this.opponentID = id_adversaire
        }
    }

    fun archiverPartie(id_gagnant: Int, id_perdant: Int) {
        if (isGameEnded && isPlayerConnected) {
            try {
                val gson = Gson()

                val jsonString = gson.toJson(game.getGameSaver())

                PartieDAO.addPartie(id_gagnant, id_perdant, jsonString, object : PartieCallback {
                    override fun onPartiesRecuperees(partieList: List<Partie>) {
                        //voir pour peut etre mettre un toast
                    }

                    override fun onPartieCree(succes: String){
                        System.out.println(succes);
                    }


                    override fun onError(e: Exception) {
                        //voir pour peut etre mettre un toast
                        System.out.println(e);
                    }
                })
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    fun playerWon():Boolean {
        var win: Boolean = true
        if(isPlayerFirst == game.whoLost()) {
            win  = false
        }
        return win
    }
}