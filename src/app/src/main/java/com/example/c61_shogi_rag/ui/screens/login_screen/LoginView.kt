package com.example.c61_shogi_rag.ui.screens.login_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.ui.screens.PlayerShareViewModel
import com.example.c61_shogi_rag.ui.theme.ShogiButton
import com.example.c61_shogi_rag.ui.theme.Title

@Composable
fun LoginView(modifier: Modifier = Modifier,
              loginViewModel: LoginViewModel = viewModel(),
              playerShareViewModel: PlayerShareViewModel,
              navigateToMainMenu:() -> Unit) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.weight(1f))
            Title(name = "Login")
            TextField(
                value = loginViewModel.username,
                onValueChange = {loginViewModel.username = it},
                label = { Text("Username") },

                )
            TextField(
                value = loginViewModel.password,
                onValueChange = { loginViewModel.password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.weight(1f))
            ShogiButton(
                name = "Submit",
                onClick = {
                    val validatedPlayer = loginViewModel.validatePlayer()
                    if(validatedPlayer == null) {

                    }
                    else {
                        playerShareViewModel.currentPlayer = validatedPlayer
                        navigateToMainMenu()
                    }
                }
            )
            Spacer(modifier = Modifier.weight(1f))
    }
}

