package com.example.c61_shogi_rag.ui.navigation

import com.example.c61_shogi_rag.engine.minimax.Difficulty
import kotlinx.serialization.Serializable
/**
 * Nom du fichier : screen_objects.kt
 * Description : Ce fichier définit les objets et classes sérialisables utilisés pour la navigation entre les écrans du jeu,
 *               facilitant le transfert des données et la gestion des états.
 * Auteur(s) : [Romeo Barraza]
 * Entête générée par Copilot
 */


@Serializable
object MainMenu

@Serializable
data class Game(val opponentID: Int, val opponentName: String, val isPlayerFirst: Boolean, val difficulty: Difficulty)

@Serializable
object Login

@Serializable
object History

@Serializable
data class ArchivedGame(val matchId: Int)

@Serializable
object Register
