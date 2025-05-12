package com.example.c61_shogi_rag

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
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


