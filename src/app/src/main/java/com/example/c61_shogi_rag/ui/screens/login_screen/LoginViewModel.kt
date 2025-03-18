package com.example.c61_shogi_rag.ui.screens.login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.entity.Joueur


class LoginViewModel: ViewModel() {
    var username by mutableStateOf("")


    public fun validatePlayer(): Joueur? {
        // TODO: Faire appel ici à la bd pour valider le nom de l'usager
        return Joueur(-1, username)
    }

}