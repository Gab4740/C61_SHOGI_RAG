package com.example.c61_shogi_rag.ui.navigation

import kotlinx.serialization.Serializable


@Serializable
object MainMenu

@Serializable
data class Game(val opponentID: Int, val opponentName: String)

@Serializable
object Login

@Serializable
object History

@Serializable
data class ArchivedGame(val matchId: Int)

@Serializable
object Register
