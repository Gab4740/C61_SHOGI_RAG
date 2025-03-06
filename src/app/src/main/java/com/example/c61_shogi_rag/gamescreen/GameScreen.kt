package com.example.c61_shogi_rag.gamescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.c61_shogi_rag.game.Board
import com.example.c61_shogi_rag.ui.theme.Shogiboard

@Composable
fun GameScreen(innerPaddingValues: PaddingValues, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(innerPaddingValues)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Shogiboard(board = Board())

    }
}