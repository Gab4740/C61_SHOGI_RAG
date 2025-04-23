package com.example.c61_shogi_rag.ui.navigation

import com.example.c61_shogi_rag.engine.minimax.Difficulty
import kotlinx.serialization.Serializable


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
