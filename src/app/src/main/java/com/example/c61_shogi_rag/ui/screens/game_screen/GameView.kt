package com.example.c61_shogi_rag.ui.screens.game_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.engine.entity.Joueur
import com.example.c61_shogi_rag.ui.screens.PlayerShareViewModel
import com.example.c61_shogi_rag.ui.theme.CapturedPieces
import com.example.c61_shogi_rag.ui.theme.PlayerTag

@Composable
fun GameView(modifier: Modifier = Modifier,
             gameViewModel: GameViewModel = viewModel(),
             playerShareViewModel: PlayerShareViewModel,
             opponent: Joueur) {
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
            playerName = opponent.nom_joueur
        )
        CapturedPieces(
            modifier = Modifier
                .padding(vertical = 5.dp)
        )
        BoardView(gameViewModel = gameViewModel)
        CapturedPieces(
            modifier = Modifier
                .padding(vertical = 5.dp),
            isClickable = true
        )
        PlayerTag(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 5.dp, vertical = 3.dp),
            playerName = playerShareViewModel.currentPlayer.nom_joueur

        )
    }
}
