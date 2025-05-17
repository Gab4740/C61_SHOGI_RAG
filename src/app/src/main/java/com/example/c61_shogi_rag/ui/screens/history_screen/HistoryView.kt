package com.example.c61_shogi_rag.ui.screens.history_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.R
import com.example.c61_shogi_rag.engine.entity.Partie
import com.example.c61_shogi_rag.engine.game.GameSaver
import com.example.c61_shogi_rag.engine.minimax.Difficulty
import com.example.c61_shogi_rag.ui.screens.PlayerShareViewModel
import com.example.c61_shogi_rag.ui.theme.CounterText
import com.example.c61_shogi_rag.ui.theme.CustomText
import com.example.c61_shogi_rag.ui.theme.PlayerTag
import com.example.c61_shogi_rag.ui.theme.ShogiButton
import com.example.c61_shogi_rag.ui.theme.Title
import com.google.gson.Gson


@Composable
fun HistoryView(
    modifier: Modifier = Modifier,
    historyViewModel: HistoryViewModel = viewModel(),
    playerShareViewModel: PlayerShareViewModel,
    navigateToGame: (Int, String, Boolean, Difficulty) -> Unit = { _, _, _, _ -> }
) {

    val uiState by historyViewModel._uiState.collectAsState()
    val joueurID = playerShareViewModel.currentPlayer.joueur_id

    LaunchedEffect(Unit) {
        historyViewModel.getPartieJouer(joueurID)
    }




    when (val state = uiState) {
        is HistoryUiState.Loading -> {
            CircularProgressIndicator()
        }

        is HistoryUiState.Success -> {
            val parties = state.parties.sortedByDescending { it.date }
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Title(name = "Saved games")
                if (playerShareViewModel.isCurrentPlayerSet()) {
                    LazyColumn {
                        items(parties) { partie ->
                            val (senteScore, goteScore) = historyViewModel.calculateScores(
                                partie,
                                playerShareViewModel.currentPlayer.joueur_id
                            )

                            val (senteName, goteName) = historyViewModel.getNom(
                                partie,
                                playerShareViewModel.currentPlayer.nom_joueur
                            )

                            MatchItem(
                                partie = partie,
                                senteName = senteName,
                                goteName = goteName,
                                senteScore = senteScore,
                                goteScore = goteScore,
                                onClick = {partieChoisi ->
                                    playerShareViewModel.selectedPartie = partieChoisi

                                    val gameSaver = Gson().fromJson(partieChoisi.historiqueCoups, GameSaver::class.java)
                                    playerShareViewModel.currentGameSaver = gameSaver

                                    navigateToGame(
                                        0,
                                        "AI",
                                        partieChoisi.isPlayerCouleur,
                                        Difficulty.Easy
                                    )
                                }
                            )

                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    }
                } else {
                    CustomText(text = "Connect to see saved games")
                }
            }
        }
        is HistoryUiState.Error -> {
        Text(
            text = state.message,
            color = MaterialTheme.colorScheme.error
        ) }
    }
}

@Composable
fun MatchItem(modifier: Modifier = Modifier,
              partie: Partie,
              senteName: String,
              goteName: String,
              senteScore: Float,
              goteScore: Float,
              onClick: (Partie) -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                if (!partie.isPartieTerminee) {
                    onClick(partie)
                }
            }
        ) {
            SenteComposable(name = senteName, score = senteScore)
            Text(text = " - ", fontSize = 40.sp)
            GoteComposable(name = goteName, score = goteScore)
        }
        Row {
            if (!partie.isPartieTerminee){
                ShogiButton(text = "Continuer",
                    onClick = { onClick(partie) })
            }
        }
    }

}

@Composable
fun SenteComposable(modifier: Modifier = Modifier, name: String, score: Float) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlayerTag(playerName = name)
        Icon(
            modifier = Modifier.size(40.dp),
            painter = painterResource(R.drawable.sente_king_0),
            contentDescription = "Image Sente",
            tint = Color.Unspecified
        )
        CounterText(value = score)
    }
}

@Composable
fun GoteComposable(modifier: Modifier = Modifier, name: String, score: Float) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CounterText(value = score)

        Icon(
            modifier = Modifier.size(40.dp),
            painter = painterResource(R.drawable.gote_king_0),
            contentDescription = "Image Sente",
            tint = Color.Unspecified
        )
        PlayerTag(playerName = name)
    }
}

@Composable
fun PartieItem(
    partie:Partie,
    historyViewModel: HistoryViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        historyViewModel.getNomJoueurs(partie)
    }

    val joueurGagnant = historyViewModel.joueursGagnants[partie.partie_id]
    val joueurPerdant = historyViewModel.joueursPerdants[partie.partie_id]
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .border(width = 2.dp, color = Color.Black, shape = RectangleShape)

    ) {
        Row (  modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Column( modifier = Modifier
                .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (joueurGagnant != null) {
                    Text(
                        text = "joueur gagnant ${joueurGagnant.nom_joueur}",
                        color = Color.Green,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Text(
                    text = " vs",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (joueurPerdant != null) {
                    Text(
                        text = "joueur perdant ${joueurPerdant.nom_joueur}",
                        color = Color.Red,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Text(
                text = partie.date,
                color = Color.Black,
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
