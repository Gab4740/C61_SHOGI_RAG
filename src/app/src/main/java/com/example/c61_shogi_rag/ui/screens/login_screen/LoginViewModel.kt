package com.example.c61_shogi_rag.ui.screens.login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class LoginViewModel: ViewModel() {
    var connectedUser by mutableStateOf("")
}