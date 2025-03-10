package com.example.c61_shogi_rag.ui.screens.game_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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
                    position -> gameViewModel.selectPiece(position)
            }
        )
        BoardLayout(
            modifier = modifier,
            boardSize = gameViewModel.game.gameBoard.boarD_SIZE,
            gameViewModel
        )
    }

}