package com.example.c61_shogi_rag.ui.screens.main_menu_screen

import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.entity.Joueur


class MainMenuViewModel(): ViewModel()  {
    val opponent: Joueur = Joueur(-2, "AI")

}