package com.example.c61_shogi_rag.ui.screens.archived_game_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.entity.Partie
import com.example.c61_shogi_rag.engine.game.TurnHistory
import com.google.gson.Gson
/**
 * Nom du fichier : ArchivedGameViewModel.kt
 * Description : Ce fichier définit un ViewModel permettant de gérer la récupération et l'affichage
 *               de l'historique des coups d'une partie archivée.
 * Auteur(s) : [Arslan Khaoua]
 * Entête générée par Copilot
 */
class ArchivedGameViewModel: ViewModel()  {


    var partie by mutableStateOf<Partie?>(null)

    var turn by mutableStateOf<TurnHistory?>(null)

    fun getListCoups(){

        turn = Gson().fromJson(partie?.historiqueCoups, TurnHistory::class.java)

    }



}