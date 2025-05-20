package com.example.c61_shogi_rag.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.entity.Joueur
import com.example.c61_shogi_rag.engine.entity.Partie
import com.example.c61_shogi_rag.engine.game.GameSaver

/**
 * Nom du fichier : PlayerShareViewModel.kt
 * Description : Ce fichier définit un ViewModel permettant de gérer les informations du joueur actuel,
 *               la partie sélectionnée et la sauvegarde des données du jeu.
 * Auteur(s) : [Romeo Barraza, Arslan Khaoua]
 * Entête générée par Copilot
 */
class PlayerShareViewModel: ViewModel() {

    var currentPlayer: Joueur by mutableStateOf(Joueur(-1, "Guest"))

    var selectedPartie: Partie by mutableStateOf(Partie(-1, -1, -1, "", "", false, false))

    var currentGameSaver: GameSaver by mutableStateOf(GameSaver())

    fun removeCurrentPlayer(): Unit {
        currentPlayer = Joueur(-1, "Guest")
    }

    fun isCurrentPlayerSet():Boolean {
        return currentPlayer.nom_joueur != "Guest"
    }

    fun removeSelectPartie(): Unit{
        selectedPartie = Partie(-1, -1, -1, "", "", false, false)
        //selectedPartie = Partie(-1, -1, -1, "", "")
    }

    fun isSelectPartieSet():Boolean{
        return selectedPartie.partie_id != -1
    }




}