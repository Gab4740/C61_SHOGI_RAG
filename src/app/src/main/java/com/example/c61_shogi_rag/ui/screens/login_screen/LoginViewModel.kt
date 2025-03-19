package com.example.c61_shogi_rag.ui.screens.login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.entity.Joueur


class LoginViewModel: ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var registerMode by mutableStateOf(false)


    public fun getCurrentMode(): String {
        return if(registerMode) "Register" else "Login"
    }
    public fun getAlternativeMode(): String {
        return  if(!registerMode) "Register" else "Login"
    }

    public fun toggleMode(): Unit {
        registerMode = !registerMode
    }

    public fun registerPlayer(): Boolean {
        // TODO: Faire appel ici à la bd pour enregistrer un usager
        return true;
    }
    public fun validatePlayer(): Joueur? {
        // TODO: Faire appel ici à la bd pour valider le nom de l'usager
        return Joueur(-1, username)
    }
}