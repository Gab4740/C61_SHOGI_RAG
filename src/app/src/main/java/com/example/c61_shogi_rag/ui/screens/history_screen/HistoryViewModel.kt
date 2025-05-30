package com.example.c61_shogi_rag.ui.screens.history_screen

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c61_shogi_rag.engine.dao.JoueurDAO
import com.example.c61_shogi_rag.engine.dao.PartieDAO
import com.example.c61_shogi_rag.engine.entity.Joueur
import com.example.c61_shogi_rag.engine.entity.JoueurCallback
import com.example.c61_shogi_rag.engine.entity.Partie
import com.example.c61_shogi_rag.engine.entity.PartieCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * Nom du fichier : HistoryViewModel.kt
 * Description : Ce fichier définit un ViewModel permettant de gérer l'historique des parties jouées,
 *               incluant la récupération des données de la base de données et l'affichage des scores.
 * Auteur(s) : [Arslan Khaoua]
 * Entête générée par Copilot
 */
sealed class HistoryUiState {

    object Loading : HistoryUiState()
    data class Success(val parties: List<Partie>) : HistoryUiState()
    data class Error(val message: String) : HistoryUiState()
}

class HistoryViewModel : ViewModel() {


    private var uiState = MutableStateFlow<HistoryUiState>(HistoryUiState.Loading)

    // État exposé à l'UI
    val _uiState: StateFlow<HistoryUiState> = uiState.asStateFlow()

    val joueursGagnants = mutableStateMapOf<Int, Joueur?>()
    val joueursPerdants = mutableStateMapOf<Int, Joueur?>()

    val senteScore:Int = 0
    val goteScore:Int = 0

    /**
     *
     * méthode pérmetant de récuperer toutes les parties jouer du joueur connecter
     *
     * @param id_joueur id du joueur connecter
     *
     **/
    fun getPartieJouer(id_joueur: Int){
        viewModelScope.launch {
            uiState.value = HistoryUiState.Loading
            try {
                val parties = getPartieSuspend(id_joueur)
                uiState.update { HistoryUiState.Success(parties) }
            } catch (e:Exception) {
                uiState.update {HistoryUiState.Error("" +
                        "Erreur lors du chargement des donnees : ${e.message}")}
            }
        }
    }

    /**
     *
     * fonction suspendue qui permet de recuperer les donnee de la bd
     *
     * @param id_joueur id du joueur connecter
     *
     **/
    private suspend fun getPartieSuspend(id_joueur: Int): List<Partie> =
        suspendCancellableCoroutine { continuation ->
            PartieDAO.getPartie(object : PartieCallback {
                override fun onPartiesRecuperees(partieList: List<Partie>) {
                    continuation.resumeWith(Result.success(partieList))
                }

                override fun onPartieCree(succes: String){
                }

                override fun onError(exception: Exception) {
                    continuation.resumeWith(Result.failure(exception))
                }
            }, id_joueur)
    }

    /**
     *
     * méthode pérmetant de récuperer les noms des joueur gagnant et perdant  de la partie
     *
     * @param partie represente la partie actuel qu'on cherche
     *
     **/
    fun getNomJoueurs(partie: Partie) {
        viewModelScope.launch {
            try {
                if (!joueursGagnants.containsKey(partie.partie_id)) {
                    val gagnant = getJoueurByIdSuspend(partie.winner_id)
                    joueursGagnants[partie.partie_id] = gagnant
                }
                if (!joueursPerdants.containsKey(partie.partie_id)) {
                    val perdant = getJoueurByIdSuspend(partie.loser_id)
                    joueursPerdants[partie.partie_id] = perdant
                }
            }catch (e:Exception){
                Log.e("HistoryViewModel", "Erreur lors de la récuperation des noms des joueurs")
            }
        }
    }

    /**
     *
     * fonction suspendu permetant de recuperé les donnée de la bd
     *
     * @param winnerId id du joueur gagnant
     * @param loserId id du joueur perdant
     *
     **/
    private suspend fun getJoueurByIdSuspend(id_joueur: Int): Joueur? =
        suspendCancellableCoroutine { continuation ->
            JoueurDAO.getJoueurById(object : JoueurCallback {
                override fun onJoueurRecupere(joueur: Joueur?) {
                    continuation.resumeWith(Result.success(joueur))
                }

                override fun onError(message: String?) {
                    continuation.resumeWith(Result.failure(Exception(message ?: "Unknown error")))
                }

            }, id_joueur)
    }

    fun calculateScores(partie:Partie, playerId:Int): Pair<Float, Float>  {
        val isPlayerWinner = partie.winner_id == playerId
        val playerCouleur = partie.isPlayerCouleur

        return if (playerCouleur) {
            if (isPlayerWinner) Pair(1f, 0f) else Pair(0f, 1f)
        } else {
            if (isPlayerWinner) Pair(0f, 1f) else Pair(1f, 0f)
        }
    }

    fun getNom(partie: Partie, playerName: String): Pair<String, String> {
        val playerCouleur = partie.isPlayerCouleur

        return if (playerCouleur) {
            Pair(playerName, "IA")
        } else {
            Pair("IA", playerName)
        }
    }

}