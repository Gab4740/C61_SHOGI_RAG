package com.example.c61_shogi_rag.ui.screens.main_menu_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCbrt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.ui.screens.PlayerShareViewModel
import com.example.c61_shogi_rag.ui.theme.GameTitle
import com.example.c61_shogi_rag.ui.theme.GoteComposable
import com.example.c61_shogi_rag.ui.theme.GoteSenteComposable
import com.example.c61_shogi_rag.ui.theme.SenteComposable
import com.example.c61_shogi_rag.ui.theme.ShogiButton
import com.example.c61_shogi_rag.ui.theme.japanWaveFontFamily
import kotlin.random.Random


@Composable
fun MainMenuView(modifier: Modifier = Modifier,
                 mainMenuViewModel: MainMenuViewModel = viewModel(),
                 playerShareViewModel: PlayerShareViewModel,
                 navigateToGame: (Int, String, Boolean) -> Unit,
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
                mainMenuViewModel.isPlayerFirst = it
                navigateToGame(
                    mainMenuViewModel.opponent.joueur_id,
                    mainMenuViewModel.opponent.nom_joueur,
                    mainMenuViewModel.isPlayerFirst
                )
            }
        }
    }
}

@Composable
fun ThreeOptionDialog(modifier: Modifier = Modifier, onDismiss:() -> Unit = {},
                      onConfirmation:(Boolean) -> Unit)
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
                SenteComposable{onConfirmation(true)}
                GoteSenteComposable{onConfirmation(Random.nextBoolean())}
                GoteComposable{onConfirmation(false)}
            }
        }
    )
}



@Composable
fun RadioButtonSingleSelection(modifier: Modifier = Modifier) {
    val radioOptions = listOf("Easy", "Medium", "Hard")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Row(modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Column(
                Modifier
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton

                    )
                    .padding(horizontal = 16.dp)
                    .then(if (text == selectedOption) Modifier.background(Color.Yellow) else Modifier),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(start = 16.dp)

                )
            }
        }
    }
}

