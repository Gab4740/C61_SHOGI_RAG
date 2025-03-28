package com.example.c61_shogi_rag.ui.screens.main_menu_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.entity.Joueur


class MainMenuViewModel(): ViewModel()  {
    val opponent: Joueur = Joueur(-2, "AI")
    var openAlertDialog by mutableStateOf(false)
    var isPlayerFirst: Boolean = false
}