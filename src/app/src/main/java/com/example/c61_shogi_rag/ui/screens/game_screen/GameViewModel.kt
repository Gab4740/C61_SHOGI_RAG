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
    var game by mutableStateOf(Game(isPlayerFirst, difficulty.strategy))
        private set   // Permet d'accéder game à l'extérieur mais pas le modifier
    var isPlayerTurn by mutableStateOf(game.isPlayerTurn)
    var counter by mutableIntStateOf(0)
    var isGameEnded by mutableStateOf(game.isGameEnded)
    private var selectedPosition: Position? = null
    private var selectedPieceToParchute: ShogiPiece? = null
    var isPlayerFirst: Boolean = isPlayerFirst

    private var playerID: Int = -1
    private var opponentID: Int = 0
    private var isPlayerConnected: Boolean = false;

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
        if(!isGameEnded) {
            if(game.isPlayerPieceAtPos(position)) {
                selectedPosition = position
                selectedPieceToParchute = null
            } else if(selectedPosition != null) {
                if(game.playTurn(selectedPosition, position)) {
                    selectedPosition = null
                    isPlayerTurn = game.isPlayerTurn

                    isGameEnded = game.isGameEnded
                    if(isGameEnded){
                       archiverPartie(playerID, opponentID)
                        System.out.println("Game ended")
                        return
                    }else {
                        aiTurn()
                    }

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
        if(!isGameEnded) {
            viewModelScope.launch {
                game.aiTurn()
                delay(2500) // solution boff
                counter++
                isGameEnded = game.isGameEnded
//                if(isGameEnded) {
//                    System.out.println("yo")
//                    archiverPartie(opponentID, playerID)
//                }
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
        return isPlayerFirst && game.whoLost()
    }
}