package com.example.c61_shogi_rag.ui.screens.game_screen

import android.util.Log
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
import com.example.c61_shogi_rag.engine.game.GameSaver
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class GameViewModel(isPlayerFirst: Boolean, difficulty: Difficulty, savedGameSaver: GameSaver? = null):
    ViewModel() {


    //var counter by mutableIntStateOf(0)
    val counter = mutableIntStateOf(0)
    var game by mutableStateOf(Game(isPlayerFirst, difficulty.strategy, counter))
        private set   // Permet d'accéder game à l'extérieur mais pas le modifier
    var isPlayerTurn by mutableStateOf(game.isPlayerTurn)
    var isGameEnded by mutableStateOf(game.isGameEnded)
    private var selectedPosition: Position? = null
    private var selectedPieceToParchute: ShogiPiece? = null
    var isPlayerFirst: Boolean = isPlayerFirst
    var pieceToPromote: ShogiPiece? by mutableStateOf(null)
    var positionPromotedPiece: Position? = null
    var playerID: Int = -1
    var opponentID: Int = 0
    var isPlayerConnected: Boolean = false;

    val clock = mutableStateOf(game.timeString)

    init {
        game.GameInit()

        if (savedGameSaver != null){
            game.gameSaver = savedGameSaver
            game.loadSavedGame()

            isPlayerTurn = game.isPlayerTurn
            isGameEnded = game.isGameEnded

            System.out.println("ALLLO")

        }
        else if(!isPlayerTurn) {
            game.startMinimaxComputation();
            isPlayerTurn = game.isPlayerTurn
        }
        viewModelScope.launch {
            while (true) {
                clock.value = game.timeString
                delay(1000L)
            }
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
                    if(!isGameEnded) {
                        pieceToPromote = game.shouldPromote(position)
                        positionPromotedPiece = position
                    }
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

                game.gameSaver = null;


                PartieDAO.addPartie(id_gagnant, id_perdant, isPlayerFirst, jsonString, true, object : PartieCallback {
                    override fun onPartiesRecuperees(partieList: List<Partie>) {
                    }

                    override fun onPartieCree(succes: String){
                    }

                    override fun onError(e: Exception) {
                    }
                })
            } catch (exception: Exception) {
                println(exception.printStackTrace())
            }
        } else {
            println("Partie non finie ou pas de connexion internet")
        }
    }

    fun archiverPartieEnCours() {
        if (!isGameEnded && isPlayerConnected) {
            try {
                val gson = Gson()

                val jsonString = gson.toJson(game.getGameSaver())

                val idJoueur1 = playerID
                val idJoueur2 = opponentID

                PartieDAO.addPartie(idJoueur1, idJoueur2, isPlayerFirst, jsonString, false, object : PartieCallback {
                    override fun onPartiesRecuperees(partieList: List<Partie>) {
                        // Non utilisé ici
                    }

                    override fun onPartieCree(succes: String) {
                        System.out.println("Partie en cours sauvegardée: $succes")
                    }

                    override fun onError(e: Exception) {
                        System.out.println("Erreur lors de la sauvegarde de la partie en cours: $e")
                    }
                })
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    fun updatePartie(id_gagnant: Int, id_perdant: Int, id_partie:Int) {

        if (isGameEnded && isPlayerConnected) {
            try {
                val gson = Gson()
                val jsonString = gson.toJson(game.getGameSaver())

                game.gameSaver = null;

                PartieDAO.updatePartie(id_partie,id_gagnant, id_perdant,true , jsonString, object : PartieCallback {
                    override fun onPartiesRecuperees(partieList: List<Partie>) {
                    }

                    override fun onPartieCree(succes: String){
                    }

                    override fun onError(e: Exception) {
                    }
                })
            } catch (exception: Exception) {
                println(exception.printStackTrace())
            }
        } else {
            println("Partie non finie ou pas de connexion internet")
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