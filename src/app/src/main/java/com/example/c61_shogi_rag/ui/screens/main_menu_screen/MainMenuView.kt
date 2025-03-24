package com.example.c61_shogi_rag.ui.screens.main_menu_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.ui.screens.PlayerShareViewModel
import com.example.c61_shogi_rag.ui.theme.GameTitle
import com.example.c61_shogi_rag.ui.theme.GoteComposable
import com.example.c61_shogi_rag.ui.theme.GoteSenteComposable
import com.example.c61_shogi_rag.ui.theme.SenteComposable
import com.example.c61_shogi_rag.ui.theme.ShogiButton
import com.example.c61_shogi_rag.ui.theme.japanWaveFontFamily


@Composable
fun MainMenuView(modifier: Modifier = Modifier,
                 mainMenuViewModel: MainMenuViewModel = viewModel(),
                 playerShareViewModel: PlayerShareViewModel,
                 navigateToGame: (Int, String) -> Unit,
                 navigateToHistory: () -> Unit,
                 navigateToLogin: () -> Unit) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        GameTitle(name = "Shogi RAG!")
        ShogiButton(
            text = "Play",
            fontFamily = japanWaveFontFamily,
            onClick = {
                mainMenuViewModel.openAlertDialog = true
                /*
                navigateToGame(
                    mainMenuViewModel.opponent.joueur_id,
                    mainMenuViewModel.opponent.nom_joueur
                )*/
            }
        )
        ShogiButton(
            text = "History",
            fontFamily = japanWaveFontFamily,
            onClick = {
                navigateToHistory()
            },
            enabled = playerShareViewModel.isCurrentPlayerSet()
        )
        if(playerShareViewModel.isCurrentPlayerSet()) {
            ShogiButton(
                text = "Logout",
                fontFamily = japanWaveFontFamily,
                onClick = {playerShareViewModel.removeCurrentPlayer()}
            )
        }
        else {
            ShogiButton(
                text = "Login",
                fontFamily = japanWaveFontFamily,
                onClick = {navigateToLogin()}
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Connected as: ${playerShareViewModel.currentPlayer.nom_joueur}"
        )
    }

    when {
        mainMenuViewModel.openAlertDialog -> {
            ThreeOptionDialog(
                onDismiss = {mainMenuViewModel.openAlertDialog = false}
            ) {
                mainMenuViewModel.openAlertDialog = false
                navigateToGame(
                    mainMenuViewModel.opponent.joueur_id,
                    mainMenuViewModel.opponent.nom_joueur
                )
            }
        }
    }
}

@Composable
fun ThreeOptionDialog(modifier: Modifier = Modifier, onDismiss:() -> Unit = {},
                      onConfirmation:() -> Unit)
{
    AlertDialog(
        onDismissRequest = { onDismiss() },

        title = { Text(text = "Choose an Option") },
        text = { Text(text = "Please select one of the three options below:") },
        confirmButton = {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround


            ) {
                SenteComposable{onConfirmation()}
                GoteSenteComposable{onConfirmation()}
                GoteComposable{onConfirmation()}
            }
        },

        )
}





