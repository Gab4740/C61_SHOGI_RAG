package com.example.c61_shogi_rag.ui.screens.main_menu_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.ui.theme.GameTitle
import com.example.c61_shogi_rag.ui.theme.ShogiButton
import com.example.c61_shogi_rag.ui.theme.japanWaveFontFamily

@Composable
fun MainMenuView(modifier: Modifier = Modifier,
                 mainMenuViewModel: MainMenuViewModel = viewModel(),
                 navigateToGame: (String, String) -> Unit,
                 navigateToHistory: () -> Unit,
                 navigateToLogin: () -> Unit) {
    val player1 = mainMenuViewModel.connectedUser
    val player2 = mainMenuViewModel.opponent

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        GameTitle(name = "Shogi RAG!")
        ShogiButton(
            name = "Play",
            fontFamily = japanWaveFontFamily,
            onClick = {navigateToGame(player1, player2)}
        )
        ShogiButton(
            name = "History",
            fontFamily = japanWaveFontFamily,
            onClick = {navigateToHistory()}
        )
        ShogiButton(
            name = "Login",
            fontFamily = japanWaveFontFamily,
            onClick = {navigateToLogin()}
            )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Connected as: $player1"
        )
    }
}