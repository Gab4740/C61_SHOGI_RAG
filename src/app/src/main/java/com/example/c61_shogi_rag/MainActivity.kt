package com.example.c61_shogi_rag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.example.c61_shogi_rag.game.Time

import com.example.c61_shogi_rag.dao.JoueurDAO
import com.example.c61_shogi_rag.piece.InitPiece
import com.example.c61_shogi_rag.piece.Piece
import com.example.c61_shogi_rag.ui.theme.C61_SHOGI_RAGTheme

class MainActivity : ComponentActivity() {

    var joueur = JoueurDAO();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            C61_SHOGI_RAGTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "aaaaaa",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        //joueur.addJoueur("eric", "Salut92");
        joueur.getJoueur();
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    C61_SHOGI_RAGTheme {
        Greeting("Android")
    }
}

