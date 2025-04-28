package com.example.c61_shogi_rag.ui.screens.game_screen

import android.graphics.Paint.Cap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.engine.entity.Joueur
import com.example.c61_shogi_rag.ui.screens.PlayerShareViewModel
import com.example.c61_shogi_rag.ui.theme.CapturedPieces
import com.example.c61_shogi_rag.ui.theme.PlayerTag
import com.example.c61_shogi_rag.ui.theme.ShogiButton
import com.example.c61_shogi_rag.ui.theme.Title


@Composable
fun GameView(modifier: Modifier = Modifier,
             gameViewModel: GameViewModel = viewModel(),
             playerShareViewModel: PlayerShareViewModel,
             opponent: Joueur,
             navigateToMainMenu:() -> Unit
             ) {
    LaunchedEffect(key1 = Unit) {
        gameViewModel.setPlayerID(
            playerShareViewModel.isCurrentPlayerSet(),
            playerShareViewModel.currentPlayer.joueur_id,
            opponent.joueur_id
        )
    }

    key(gameViewModel.counter)
    {
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
                    .padding(vertical = 5.dp),
                isClickable = false,
                hashMapCapturedPieces = gameViewModel.game.capturedPieceBlackHM,
            )
            BoardView(gameViewModel = gameViewModel)
            CapturedPieces(
                modifier = Modifier
                    .padding(vertical = 5.dp),
                isClickable = true,
                onClick = {
                          shogiPiece -> gameViewModel.parachutePiece(shogiPiece)
                },
                hashMapCapturedPieces = gameViewModel.game.capturedPieceWhiteHM,
            )
            PlayerTag(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 5.dp, vertical = 3.dp),
                playerName = playerShareViewModel.currentPlayer.nom_joueur

            )
        }
    }
    when {
        //rajouter ici la sauvegarde de partie
        gameViewModel.isGameEnded -> {
            val winner = if (gameViewModel.playerWon()) {
                playerShareViewModel.currentPlayer.nom_joueur
            } else {
                opponent.nom_joueur
            }

            if(winner ==  playerShareViewModel.currentPlayer.nom_joueur){
                gameViewModel.archiverPartie(gameViewModel.playerID, gameViewModel.opponentID)
            }else{
                gameViewModel.archiverPartie(gameViewModel.opponentID, gameViewModel.playerID)
            }


            GameOverDialog(
                winner = winner ,
                onDismiss = {
                    gameViewModel.isGameEnded = false
                    navigateToMainMenu()
                }
            )
        }
    }
}


@Composable
fun GameOverDialog(modifier: Modifier = Modifier, onDismiss:() -> Unit = {},
                   winner: String = "guest",
                   onConfirmation:() -> Unit = {}) {
    AlertDialog(
        onDismissRequest = { onDismiss() },

        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Title(name = "Game Over")
            }

        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
               PlayerTag(playerName = "player: $winner wins")
            }
        },

        confirmButton = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ShogiButton(
                    text = "Save game",
                    onClick = {onConfirmation()}
                )
            }
        }
    )
}