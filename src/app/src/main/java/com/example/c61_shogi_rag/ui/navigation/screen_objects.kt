package com.example.c61_shogi_rag.ui.navigation

import kotlinx.serialization.Serializable


@Serializable
object MainMenu

@Serializable
data class Game(val player1:String = "Player 1", val player2:String = "Player 2")

@Serializable
object History

@Serializable
data class ArchivedGame(val match: String)
