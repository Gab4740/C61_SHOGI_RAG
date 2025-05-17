package com.example.c61_shogi_rag.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.c61_shogi_rag.engine.entity.Joueur
import com.example.c61_shogi_rag.ui.screens.PlayerShareViewModel
import com.example.c61_shogi_rag.ui.screens.archived_game_screen.ArchivedGameView
import com.example.c61_shogi_rag.ui.screens.game_screen.GameView
import com.example.c61_shogi_rag.ui.screens.game_screen.GameViewModel
import com.example.c61_shogi_rag.ui.screens.history_screen.HistoryView
import com.example.c61_shogi_rag.ui.screens.login_screen.LoginView
import com.example.c61_shogi_rag.ui.screens.main_menu_screen.MainMenuView

@Composable
fun NavigationWrapper(modifier: Modifier = Modifier,
                      onGameViewModel: (GameViewModel) -> Unit = {}) {

    val navController = rememberNavController()
    val playerShareViewModel: PlayerShareViewModel = viewModel()

    NavHost(navController = navController, startDestination = MainMenu) {
        composable<MainMenu> {

            val mainMenu:MainMenu = it.toRoute()

            MainMenuView(
                modifier = modifier,
                playerShareViewModel = playerShareViewModel,
                navigateToGame = {
                        player1, player2, isPlayerFirst, difficulty ->
                    navController.navigate(Game(player1, player2, isPlayerFirst, difficulty)) },
                navigateToHistory = {
                    navController.navigate(History) },
                navigateToLogin = {navController.navigate(Login)}
            )
        }
        composable<Game> {
            val game:Game = it.toRoute()

            val gameViewModel = viewModel {
                val savedGame = playerShareViewModel.currentGameSaver
                if (savedGame != null && playerShareViewModel.isSelectPartieSet()) {
                    GameViewModel(game.isPlayerFirst, game.difficulty, savedGame)
                } else
                GameViewModel(game.isPlayerFirst, game.difficulty)
            }

            LaunchedEffect(gameViewModel) {
                onGameViewModel(gameViewModel)

            }

            GameView(
                modifier = modifier,
                playerShareViewModel = playerShareViewModel,
                opponent = Joueur(game.opponentID, game.opponentName),
                gameViewModel = gameViewModel,
                navigateToMainMenu = {

                    navController.navigate(MainMenu) {
                        popUpTo<MainMenu>{inclusive = true}
                    }
                }
            )
        }

        composable<History> {
            HistoryView(
                modifier = modifier,
                playerShareViewModel = playerShareViewModel,
                navigateToGame = { opponentID, opponentName, isPlayerFirst, difficulty ->
                    navController.navigate(
                        Game(opponentID, opponentName, isPlayerFirst, difficulty)
                    ) {
                        popUpTo<MainMenu> { inclusive = false }
                    }
                }
            )
        }

        composable<Login> {
            LoginView(
                playerShareViewModel = playerShareViewModel,
                navigateToMainMenu = {
                    navController.navigate(MainMenu) {
                        popUpTo<MainMenu>{inclusive = true}
                    }
                }
            )
        }
        composable<ArchivedGame> {
            ArchivedGameView(
                playerShareViewModel = playerShareViewModel,
            )
        }
    }
}
