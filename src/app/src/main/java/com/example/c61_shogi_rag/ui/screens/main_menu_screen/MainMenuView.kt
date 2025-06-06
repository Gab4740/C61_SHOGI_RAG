package com.example.c61_shogi_rag.ui.screens.main_menu_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.engine.game.GameSaver
import com.example.c61_shogi_rag.engine.minimax.Difficulty
import com.example.c61_shogi_rag.ui.screens.PlayerShareViewModel
import com.example.c61_shogi_rag.ui.theme.DifficultyOption
import com.example.c61_shogi_rag.ui.theme.GameTitle
import com.example.c61_shogi_rag.ui.theme.GoteComposable
import com.example.c61_shogi_rag.ui.theme.GoteSenteComposable
import com.example.c61_shogi_rag.ui.theme.SenteComposable
import com.example.c61_shogi_rag.ui.theme.ShogiButton
import com.example.c61_shogi_rag.ui.theme.japanWaveFontFamily
import kotlin.random.Random

/**
 * Nom du fichier : MainMenuView.kt
 * Description : Ce fichier contient les composants et la logique permettant d'afficher et d'interagir
 *               avec le menu principal du jeu Shogi, incluant la gestion des boutons et des options de jeu.
 * Auteur(s) : [Romeo Barraza]
 * Entête générée par Copilot
 */

@Composable
fun MainMenuView(modifier: Modifier = Modifier,
                 mainMenuViewModel: MainMenuViewModel = viewModel(),
                 playerShareViewModel: PlayerShareViewModel,
                 navigateToGame: (Int, String, Boolean, Difficulty) -> Unit,
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
                playerShareViewModel.removeSelectPartie()
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
                onDismiss = {mainMenuViewModel.openAlertDialog = false},
                mainMenuViewModel = mainMenuViewModel
            ) {
                mainMenuViewModel.openAlertDialog = false
                mainMenuViewModel.isPlayerFirst = it
                navigateToGame(
                    mainMenuViewModel.opponent.joueur_id,
                    mainMenuViewModel.opponent.nom_joueur,
                    mainMenuViewModel.isPlayerFirst,
                    mainMenuViewModel.selectedDifficulty
                )
            }
        }
    }
}

@Composable
fun ThreeOptionDialog(modifier: Modifier = Modifier, mainMenuViewModel: MainMenuViewModel,
                      onDismiss:() -> Unit = {},
                      onConfirmation:(Boolean) -> Unit,)
{
    AlertDialog(
        onDismissRequest = { onDismiss() },

        title = { Text(text = "Game Options") },
        confirmButton = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioButtonSingleSelection(mainMenuViewModel = mainMenuViewModel)
                Spacer(modifier = Modifier.padding(15.dp))
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
        }
    )
}


@Composable
fun RadioButtonSingleSelection(modifier: Modifier = Modifier, mainMenuViewModel: MainMenuViewModel) {
    Row(
        modifier
            .selectableGroup()
    ) {
        mainMenuViewModel.difficultyOptions.forEach {difficulty: Difficulty ->
            Column(
                modifier = Modifier
                    .selectable(
                        selected = (difficulty.strategyString == mainMenuViewModel.selectedDifficulty.strategyString),
                        onClick = { mainMenuViewModel.onDifficultySelected(difficulty) },
                        role = Role.RadioButton
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            )  {
               DifficultyOption(
                   difficulty = difficulty,
                   isSelected = difficulty.strategyString == mainMenuViewModel.selectedDifficulty.strategyString
               )
            }
        }
    }
}

