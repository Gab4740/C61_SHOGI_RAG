package com.example.c61_shogi_rag.ui.screens.game_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.c61_shogi_rag.ui.theme.BoardBackground
import com.example.c61_shogi_rag.ui.theme.BoardLayout
/**
 * Nom du fichier : BoardView.kt
 * Description : Ce fichier définit le composant d'affichage du plateau de jeu du Shogi,
 *               permettant l'interaction avec les cases et l'affichage des pièces en fonction du ViewModel.
 * Auteur(s) : [Romeo Barraza]
 * Entête générée par Copilot
 */
@Composable
fun BoardView(modifier: Modifier = Modifier, gameViewModel: GameViewModel) {
    Box {
        BoardBackground(
            modifier = modifier,
            boardSize = gameViewModel.game.gameBoard.boarD_SIZE,
            onTap = {
                    position -> gameViewModel.selectPosition(position)
            }
        )
        BoardLayout(
            modifier = modifier,
            boardSize = gameViewModel.game.gameBoard.boarD_SIZE,
            gameViewModel
        )
    }

}