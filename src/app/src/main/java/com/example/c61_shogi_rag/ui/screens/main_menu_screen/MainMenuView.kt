package com.example.c61_shogi_rag.ui.screens.main_menu_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.ui.theme.GameTitle
import com.example.c61_shogi_rag.ui.theme.MenuButton
import com.example.c61_shogi_rag.ui.theme.japanWaveFontFamily

@Composable
fun MainMenuView(modifier: Modifier = Modifier,
                 mainMenuViewModel: MainMenuViewModel = viewModel(),
                 navigateToGame: (String, String) -> Unit,
                 navigateToHistory: () -> Unit) {
    var player1 = "Player1"
    var player2 = "Player2"
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        GameTitle(name = "Shogi RAG!")
        MenuButton(
            name = "Play",
            fontFamily = japanWaveFontFamily,
            onClick = {navigateToGame(player1, player2)}
        )
        MenuButton(
            name = "History",
            fontFamily = japanWaveFontFamily,
            onClick = {navigateToHistory()}
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}