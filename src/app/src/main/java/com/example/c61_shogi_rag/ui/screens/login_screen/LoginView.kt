package com.example.c61_shogi_rag.ui.screens.login_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.ui.screens.PlayerShareViewModel
import com.example.c61_shogi_rag.ui.theme.ShogiButton
import com.example.c61_shogi_rag.ui.theme.Title
/**
 * Nom du fichier : LoginView.kt
 * Description : Ce fichier définit l'interface utilisateur de la connexion et de l'inscription des joueurs,
 *               incluant la gestion des champs de saisie, des validations et de la navigation vers le menu principal.
 * Auteur(s) : [Arslan Khaoua, Romeo Barraza]
 * Entête générée par Copilot
 */
@Composable
fun LoginView(modifier: Modifier = Modifier,
              loginViewModel: LoginViewModel = viewModel(),
              playerShareViewModel: PlayerShareViewModel,
              navigateToMainMenu:() -> Unit) {

    val isLoading = loginViewModel.isLoading.value

    LaunchedEffect(loginViewModel.joueurRecuperer) {
        loginViewModel.joueurRecuperer?.let { joueur ->
            playerShareViewModel.currentPlayer = joueur
            navigateToMainMenu()
        }

    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.weight(1f))
            Title(name = loginViewModel.getCurrentMode())
            TextField(
                value = loginViewModel.username,
                onValueChange = {loginViewModel.username = it},
                label = { Text("Username") }
            )
            TextField(
                value = loginViewModel.password,
                onValueChange = { loginViewModel.password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        if(loginViewModel.registerMode) {
            TextField(
                value = loginViewModel.confirmPassword,
                onValueChange = { loginViewModel.confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
        loginViewModel.errorMessage?.let { error->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        loginViewModel.successMessage?.let { error->
            Text(
                text = error,
                color = Color.Green,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    Spacer(modifier = Modifier.weight(1f))
    ShogiButton(
        text = "Submit",
        onClick = {
                if (loginViewModel.registerMode) {
                    loginViewModel.registerPlayer()

                } else {
                    loginViewModel.validatePlayer()
                }


        }, enabled = !isLoading )

        if (isLoading) {
            CircularProgressIndicator()
        }

        ShogiButton(
            text = loginViewModel.getAlternativeMode(),
            onClick = {loginViewModel.toggleMode()}
        )
        Spacer(modifier = Modifier.weight(1f))
    }

}

