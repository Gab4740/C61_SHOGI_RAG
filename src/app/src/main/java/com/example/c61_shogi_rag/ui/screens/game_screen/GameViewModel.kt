package com.example.c61_shogi_rag.ui.screens.game_screen

import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.game.Game

class GameViewModel: ViewModel(){
    private var game: Game = Game(true)
    init {
        game.GameInit()
    }
}