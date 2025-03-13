package com.example.c61_shogi_rag.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.entity.Joueur


class PlayerShareViewModel: ViewModel() {

    var currentPlayer: Joueur by mutableStateOf(Joueur(-1, "Guest"))


    fun removeCurrentPlayer(): Unit {
        currentPlayer = Joueur(-1, "Guest")
    }

    fun isCurrentPlayerSet():Boolean {
        return currentPlayer.nom_joueur != "Guest"
    }
}