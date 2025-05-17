package com.example.c61_shogi_rag.ui.screens.game_screen

import android.graphics.Paint.Cap
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.engine.entity.Joueur
import com.example.c61_shogi_rag.engine.piece.ShogiPiece
import com.example.c61_shogi_rag.ui.screens.PlayerShareViewModel
import com.example.c61_shogi_rag.ui.theme.CapturedPieces
import com.example.c61_shogi_rag.ui.theme.CounterText
import com.example.c61_shogi_rag.ui.theme.PlayerTag
import com.example.c61_shogi_rag.ui.theme.SenteComposable
import com.example.c61_shogi_rag.ui.theme.ShogiButton
import com.example.c61_shogi_rag.ui.theme.ShogiPieceComposable
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

    LifeCylceEvents(
        onAppStopped = {
            // Fonction appelée lorsque l'app s'arrête ou passe en arrière-plan
            Log.d("MyApp", "L'application est maintenant en arrière-plan.")
        },
        gameViewModel = gameViewModel
    )

    val gameSaved = remember { mutableStateOf(false) }

    key(gameViewModel.counter.value)
    {
        Column(
            modifier = modifier
                .padding(horizontal = 1.dp, vertical = 5.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if(gameViewModel.clock.value != null){
                CounterText(value = gameViewModel.clock.value, fontSize = 30)
            }else{
                CounterText(value = "00 : 00", fontSize = 30)
            }

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

            if (!gameSaved.value){
                if (winner == playerShareViewModel.currentPlayer.nom_joueur) {
                    gameViewModel.archiverPartie(gameViewModel.playerID, gameViewModel.opponentID)
                } else {
                    gameViewModel.archiverPartie(gameViewModel.opponentID, gameViewModel.playerID)
                }
                gameSaved.value = true
            }
            GameOverDialog(
                winner = winner ,
                onDismiss = {
                    gameViewModel.isGameEnded = false
                    gameSaved.value = false
                    navigateToMainMenu()
                }
            )
        }
        gameViewModel.pieceToPromote != null && !gameViewModel.isGameEnded  -> {
            PromotionDialogue(
                shogiPiece = gameViewModel.pieceToPromote!!,
                onDismiss = {gameViewModel.pieceToPromote = null}
            ) {
                gameViewModel.game.promotePiece(gameViewModel.positionPromotedPiece)
                gameViewModel.pieceToPromote = null
            }
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
               PlayerTag(playerName = "Player $winner wins")
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

@Composable
fun PromotionDialogue(
    modifier: Modifier = Modifier,
    shogiPiece: ShogiPiece,
    onDismiss: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ShogiPieceComposable(
                    pieceId = shogiPiece.imagE_ID,
                    onClick = {onDismiss()}
                )
                ShogiPieceComposable(
                    pieceId = shogiPiece.imagE_ID_PROMU,
                    onClick = {onConfirmation()}
                    )
            }
        }
    )
}

@Composable
fun LifeCylceEvents(onAppStopped: () -> Unit, gameViewModel: GameViewModel){
    val lifecycleOwner = LocalLifecycleOwner.current

    // Observer pour les événements de cycle de vie
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_DESTROY -> {
                    gameViewModel.archiverPartieEnCours()
                    Log.d("MyApp", "L'activité a été détruite.")
                    onAppStopped()
                }
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        // Enlève l'observer lors de la destruction de l'effet
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


}


