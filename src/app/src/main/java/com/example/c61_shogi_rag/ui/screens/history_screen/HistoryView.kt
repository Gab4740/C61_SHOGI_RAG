package com.example.c61_shogi_rag.ui.screens.history_screen

<<<<<<< HEAD
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
=======
>>>>>>> 6b1fa2b2b42c47bfd2abe485a13c7a22e40faffb
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
<<<<<<< HEAD
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
=======
import androidx.compose.material3.Icon
>>>>>>> 6b1fa2b2b42c47bfd2abe485a13c7a22e40faffb
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
<<<<<<< HEAD
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
=======
import androidx.compose.ui.res.painterResource

>>>>>>> 6b1fa2b2b42c47bfd2abe485a13c7a22e40faffb
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
<<<<<<< HEAD

    val uiState by historyViewModel._uiState.collectAsState()

    LaunchedEffect(joueur_id) {
        historyViewModel.getPartieJouer(joueur_id)
=======
    val joueurID = playerShareViewModel.currentPlayer.joueur_id
    LaunchedEffect(Unit) {
        historyViewModel.getPartieJouer(joueurID)
>>>>>>> 6b1fa2b2b42c47bfd2abe485a13c7a22e40faffb
    }


<<<<<<< HEAD
    when (val state = uiState){
        is HistoryUiState.Loading -> {
            CircularProgressIndicator()
        }
        is HistoryUiState.Success -> {
            val parties = state.parties.sortedByDescending { it.date }

            Column(
                modifier = modifier.padding(10.dp)
                ) {
                Text(
                    text = "Historique des dernieres parties",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 18.dp),
                    fontSize = 25.sp
                )

                LazyColumn {
                    items(parties, key = { it.partie_id}) { partie ->
                        PartieItem(partie = partie, historyViewModel = historyViewModel)
                        Spacer(modifier = Modifier.height(15.dp))
                    }
=======
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
>>>>>>> 6b1fa2b2b42c47bfd2abe485a13c7a22e40faffb
                }
            }
        } else {
            CustomText(text = "Connect to see saved games")
        }
        is HistoryUiState.Error -> {
            Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
<<<<<<< HEAD
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


=======
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
>>>>>>> 6b1fa2b2b42c47bfd2abe485a13c7a22e40faffb
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