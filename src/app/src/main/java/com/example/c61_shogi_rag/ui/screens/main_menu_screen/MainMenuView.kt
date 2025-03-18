package com.example.c61_shogi_rag.ui.screens.main_menu_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.engine.dao.JoueurDAO
import com.example.c61_shogi_rag.engine.entity.Joueur
import com.example.c61_shogi_rag.engine.entity.Partie
import com.example.c61_shogi_rag.ui.theme.GameTitle
import com.example.c61_shogi_rag.ui.theme.ShogiButton
import com.example.c61_shogi_rag.ui.theme.japanWaveFontFamily


@Composable
fun MainMenuView(modifier: Modifier = Modifier,
                 mainMenuViewModel: MainMenuViewModel = viewModel(),
                 navigateToGame: (String, String) -> Unit,
                 navigateToHistory: (Int) -> Unit,
                 navigateToLogin: () -> Unit) {
    //val player1 = mainMenuViewModel.connectedUser

    LaunchedEffect(Unit) {
        mainMenuViewModel.getJoueur()
    }

    val player1 = mainMenuViewModel.joueurRecuperer

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
            onClick = {
                if (player1 != null) {
                    navigateToGame(player1.nom_joueur, player2)
                }
            }
        )
        ShogiButton(
            name = "History",
            fontFamily = japanWaveFontFamily,
            onClick = {
                if (player1 != null) {
                    println(">>> Joueur envoyé à History: ${player1.nom_joueur}")
                    navigateToHistory(player1.joueur_id)
                }
            }
        )
        ShogiButton(
            name = "Login",
            fontFamily = japanWaveFontFamily,
            onClick = {navigateToLogin()}
            )
        Spacer(modifier = Modifier.weight(1f))
        if (player1 != null) {
            Text(
                text = "Connected as: ${player1.nom_joueur}"
            )
        }
    }
}




