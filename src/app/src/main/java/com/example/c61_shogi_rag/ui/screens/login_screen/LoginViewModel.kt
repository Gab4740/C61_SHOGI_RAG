package com.example.c61_shogi_rag.ui.screens.login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.dao.JoueurDAO
import com.example.c61_shogi_rag.engine.entity.Joueur
import com.example.c61_shogi_rag.engine.entity.JoueurCallback


class LoginViewModel: ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var registerMode by mutableStateOf(false)

    var joueurRecuperer by mutableStateOf<Joueur?>(null)

    var isLoading = mutableStateOf(false)

    var errorMessage by mutableStateOf<String?>(null)
    var successMessage by mutableStateOf<String?>(null)

    public fun getCurrentMode(): String {
        return if(registerMode) "Register" else "Login"
    }
    public fun getAlternativeMode(): String {
        return  if(!registerMode) "Register" else "Login"
    }

    public fun toggleMode(): Unit {
        registerMode = !registerMode
    }

    public fun registerPlayer() {
        errorMessage = null
        successMessage = null

        when {
            username.isEmpty() -> {
                errorMessage = "Veuillez entrer un nom d'utilisateur"
                return
            }
            password.isEmpty() -> {
                errorMessage = "Veuillez entrer un mot de passe"
                return
            }
            password != confirmPassword -> {
                errorMessage = "Les mots de passe ne correspondent pas"
                return
            }
        }

        isLoading.value = true

        try {
            JoueurDAO.addJoueur(username, password, object : JoueurCallback{
                override fun onJoueurRecupere(joueur: Joueur?) {
                    successMessage = "Inscription réussie"
                    isLoading.value = false

                }

                override fun onError(message: String) {
                    errorMessage = message
                    isLoading.value = false
                }
            })
        }catch (exception: Exception){
            exception.printStackTrace()
            errorMessage = "Erreur serveur : ${exception.message}"
            isLoading.value = false
        }

    }

    public fun validatePlayer(){

        isLoading.value = true
        errorMessage = null

        try {
            JoueurDAO.getJoueurByName(object : JoueurCallback {
                override fun onJoueurRecupere(joueurRecupe: Joueur) {
                    joueurRecuperer = joueurRecupe
                    isLoading.value = false
                }

                override fun onError(message: String) {
                    joueurRecuperer = null
                    errorMessage = message
                    isLoading.value = false
                }
            }, username , password)

        } catch (exception: Exception){
            exception.printStackTrace()
            errorMessage = "Erreur serveur : ${exception.message}"
            isLoading.value = false
        }


    }
}