package com.example.c61_shogi_rag.ui.screens.game_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.c61_shogi_rag.engine.game.Board
import com.example.c61_shogi_rag.ui.theme.Shogiboard

@Composable
fun GameView(modifier: Modifier = Modifier, player1: String, player2: String) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Shogiboard(board = Board())

    }
}