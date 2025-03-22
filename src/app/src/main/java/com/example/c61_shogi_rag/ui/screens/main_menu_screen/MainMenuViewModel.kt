package com.example.c61_shogi_rag.ui.screens.main_menu_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.entity.Joueur
import com.example.c61_shogi_rag.engine.dao.JoueurDAO
import com.example.c61_shogi_rag.engine.dao.PartieDAO
import com.example.c61_shogi_rag.engine.entity.JoueurCallback
import com.example.c61_shogi_rag.engine.entity.Partie
import com.example.c61_shogi_rag.engine.entity.PartieCallback


class MainMenuViewModel(): ViewModel()  {
    val opponent: Joueur = Joueur(-2, "AI")

    var joueurRecuperer by mutableStateOf<Joueur?>(null)
        private set
}