package com.example.c61_shogi_rag.ui.screens.archived_game_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.ui.screens.PlayerShareViewModel
/**
 * Nom du fichier : ArchivedGameView.kt
 * Description : Ce fichier définit l'interface utilisateur permettant d'afficher une partie archivée,
 *               en récupérant et affichant l'historique des coups joués.
 * Auteur(s) : [Arslan Khaoua]
 * Entête générée par Copilot
 */
@Composable
fun ArchivedGameView(modifier: Modifier = Modifier,
                     playerShareViewModel: PlayerShareViewModel,
                     archivedGameViewModel: ArchivedGameViewModel = viewModel(),
) {

    LaunchedEffect(Unit) {
        archivedGameViewModel.partie = playerShareViewModel.selectedPartie
        archivedGameViewModel.getListCoups()
    }


    
}