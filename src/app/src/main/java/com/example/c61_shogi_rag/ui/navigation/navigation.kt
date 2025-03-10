package com.example.c61_shogi_rag.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.c61_shogi_rag.ui.screens.archived_game_screen.ArchivedGameView
import com.example.c61_shogi_rag.ui.screens.game_screen.GameView
import com.example.c61_shogi_rag.ui.screens.history_screen.HistoryView
import com.example.c61_shogi_rag.ui.screens.login_screen.LoginView
import com.example.c61_shogi_rag.ui.screens.main_menu_screen.MainMenuView
import com.example.c61_shogi_rag.ui.screens.main_menu_screen.MainMenuViewModel

@Composable
fun NavigationWrapper(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainMenu()) {
        composable<MainMenu> {

            val mainMenu:MainMenu = it.toRoute()

            MainMenuView(
                modifier = modifier,
                mainMenuViewModel = MainMenuViewModel(mainMenu.connectedPlayer),
                navigateToGame = {
                        player1, player2 -> navController.navigate(Game(player1, player2)) },
                navigateToHistory = { navController.navigate(History) },
                navigateToLogin = {navController.navigate(Login)}
            )
        }
        composable<Game> {
            val game:Game = it.toRoute()
            GameView(
                modifier = modifier,
                player1 = game.player1,
                player2 = game.player2
            )
        }

        composable<History> {
            HistoryView(modifier)
        }

        composable<Login> {
            LoginView(
                navigateToMainMenu = {
                    connectedUser -> navController.navigate(MainMenu(connectedUser)) {
                        popUpTo<MainMenu>{inclusive = true}
                    }
                }
            )
        }
        composable<ArchivedGame> {
            ArchivedGameView(modifier)
        }
    }
}