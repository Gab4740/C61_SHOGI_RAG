package com.example.c61_shogi_rag.ui.screens.history_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.R
import com.example.c61_shogi_rag.engine.entity.Partie
import com.example.c61_shogi_rag.ui.screens.PlayerShareViewModel
import com.example.c61_shogi_rag.ui.theme.CounterText
import com.example.c61_shogi_rag.ui.theme.CustomText
import com.example.c61_shogi_rag.ui.theme.PlayerTag
import com.example.c61_shogi_rag.ui.theme.Title

@Composable
fun HistoryView(
    modifier: Modifier = Modifier,
    historyViewModel: HistoryViewModel = viewModel(),
    playerShareViewModel: PlayerShareViewModel
){
    val joueurID = playerShareViewModel.currentPlayer.joueur_id
    LaunchedEffect(Unit) {
        historyViewModel.getPartieJouer(joueurID)
    }

    val parties:List<Partie> = historyViewModel.listeParties.sortedByDescending { it.date }

    Column(
        modifier = modifier
                .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Title(name = "Saved games")
        if(playerShareViewModel.isCurrentPlayerSet()) {
            LazyColumn {
                items(parties) { partie ->

                    PartieItem(partie = partie)
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        } else {
            CustomText(text = "Connect to see saved games")
        }
    }
}


@Composable
fun MatchItem(modifier: Modifier = Modifier,
              senteName: String,
              goteName: String,
              senteScore: Float,
              goteScore: Float
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        SenteComposable(name = senteName, score = senteScore)
        Text(text = " - ", fontSize = 40.sp)
        GoteComposable(name = goteName, score = goteScore)
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
fun PartieItem(partie:Partie){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .wrapContentHeight(Alignment.CenterVertically)
    ) {
        Row (  modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .wrapContentHeight(Alignment.CenterVertically)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "joueur gagnant ${partie.winner_id}",
                    color = Color.Green,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = " vs",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "joueur perdante ${partie.loser_id}",
                    color = Color.Red,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = partie.date,
                color = Color.Black,
                fontSize = 15.sp,
                style = MaterialTheme.typography.bodyLarge


            )
        }
    }
}