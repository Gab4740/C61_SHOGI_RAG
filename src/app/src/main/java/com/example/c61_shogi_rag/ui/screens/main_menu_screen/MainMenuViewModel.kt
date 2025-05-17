package com.example.c61_shogi_rag.ui.screens.main_menu_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.entity.Joueur
import com.example.c61_shogi_rag.engine.minimax.Difficulty


class MainMenuViewModel(): ViewModel()  {
    val opponent: Joueur = Joueur(0, "AI")
    var openAlertDialog by mutableStateOf(false)
    var isPlayerFirst: Boolean = false

    val difficultyOptions = listOf(Difficulty.Easy, Difficulty.Medium, Difficulty.Hard)
    var selectedDifficulty by mutableStateOf(difficultyOptions[0])

    fun onDifficultySelected(difficulty: Difficulty) {
        selectedDifficulty = difficulty
    }
}