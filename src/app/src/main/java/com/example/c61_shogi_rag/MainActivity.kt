package com.example.c61_shogi_rag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.c61_shogi_rag.ui.navigation.NavigationWrapper
import com.example.c61_shogi_rag.ui.screens.game_screen.GameViewModel
import com.example.c61_shogi_rag.ui.theme.C61_SHOGI_RAGTheme

/**
 * Nom du fichier : MainActivity.kt
 * Description : Ce fichier définit l'activité principale de l'application,
 *               incluant la gestion du thème, la navigation et l'initialisation du ViewModel de jeu.
 * Auteur : Romeo Barraza
 * Entête générée par Copilot
 */
class MainActivity : ComponentActivity() {

    private var activeGameViewModel: GameViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            C61_SHOGI_RAGTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationWrapper(
                        Modifier.padding(innerPadding),
                        onGameViewModel = { viewModel ->
                            activeGameViewModel = viewModel
                        }
                    )
                }
            }
        }
    }

}


