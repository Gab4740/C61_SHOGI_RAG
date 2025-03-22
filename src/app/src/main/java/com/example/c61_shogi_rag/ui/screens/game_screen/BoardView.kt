package com.example.c61_shogi_rag.ui.screens.game_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.c61_shogi_rag.ui.theme.BoardBackground
import com.example.c61_shogi_rag.ui.theme.BoardLayout

@Composable
fun BoardView(modifier: Modifier = Modifier, gameViewModel: GameViewModel) {
    Box {
        BoardBackground(
            modifier = modifier,
            boardSize = gameViewModel.game.gameBoard.boarD_SIZE,
            onTap = {
                    position -> gameViewModel.selectPosition(position)
            }
        )
        BoardLayout(
            modifier = modifier,
            boardSize = gameViewModel.game.gameBoard.boarD_SIZE,
            gameViewModel
        )
    }

}