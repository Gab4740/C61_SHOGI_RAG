package com.example.c61_shogi_rag.ui.screens.game_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.ui.theme.PlayerTag
import com.example.c61_shogi_rag.ui.theme.BoardBackground

@Composable
fun GameView(modifier: Modifier = Modifier,
             gameViewModel: GameViewModel = viewModel(),
             player1: String, player2: String) {
    Column(
        modifier = modifier
            .padding(horizontal = 1.dp, vertical = 5.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PlayerTag(
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 5.dp, vertical = 3.dp),
            playerName = player2
        )
        BoardView(gameViewModel = gameViewModel)
        PlayerTag(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 5.dp, vertical = 3.dp),
            playerName = player1
        )
    }
}
