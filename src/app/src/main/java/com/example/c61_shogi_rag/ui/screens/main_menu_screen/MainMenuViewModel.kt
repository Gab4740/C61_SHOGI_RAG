package com.example.c61_shogi_rag.ui.screens.main_menu_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainMenuViewModel(connectedUser:String = "Guest"): ViewModel()  {
    var connectedUser by mutableStateOf(connectedUser)
    var opponent by mutableStateOf("AI")
}