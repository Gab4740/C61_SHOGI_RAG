package com.example.c61_shogi_rag.ui.screens.game_screen

import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.game.Board
import com.example.c61_shogi_rag.engine.game.Game

class GameViewModel: ViewModel() {
    var board = Board()
}