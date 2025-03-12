package com.example.c61_shogi_rag.ui.screens.history_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.engine.entity.Partie
import org.w3c.dom.Text

@Composable
fun HistoryView(
    modifier: Modifier = Modifier,
    historyViewModel: HistoryViewModel = viewModel(),
    joueur_id: Int
){

    LaunchedEffect(Unit) {
        historyViewModel.getPartieJouer(joueur_id)
    }

    val parties:List<Partie> = historyViewModel.listeParties.sortedByDescending { it.date }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Historique des dernieres partie jouer",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        LazyColumn {
            items(parties) { partie ->
                PartieItem(partie = partie)
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
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