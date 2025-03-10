package com.example.c61_shogi_rag.ui.screens.game_screen

import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.game.Board

class GameViewModel: ViewModel() {
    var board = Board()
}