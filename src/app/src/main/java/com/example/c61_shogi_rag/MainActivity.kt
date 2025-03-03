package com.example.c61_shogi_rag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.example.c61_shogi_rag.dao.JoueurDAO
import com.example.c61_shogi_rag.ui.theme.C61_SHOGI_RAGTheme

class MainActivity : ComponentActivity() {

    var joueur = JoueurDAO();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            C61_SHOGI_RAGTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //MainMenuScreen(innerPadding)
                    GameScreen(innerPadding)
                }
            }
        }

        //joueur.addJoueur("eric", "Salut92");

        joueur.getJoueur { joueur ->
            if (joueur != null) {
                System.out.println(joueur.nom_joueur)
            }
        };
    }


}

