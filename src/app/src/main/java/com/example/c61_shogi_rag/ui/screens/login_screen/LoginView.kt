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
import kotlin.math.log

@Composable
fun LoginView(modifier: Modifier = Modifier,
              loginViewModel: LoginViewModel = viewModel(),
              playerShareViewModel: PlayerShareViewModel,
              navigateToMainMenu:() -> Unit) {

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
            Spacer(modifier = Modifier.weight(1f))
            ShogiButton(
                text = "Submit",
                onClick = {
                    if(loginViewModel.registerMode) {
                        loginViewModel.validatePlayer()
                    } else {
                        val validatedPlayer = loginViewModel.validatePlayer()
                        if(validatedPlayer == null) {

                        }
                        else {
                            playerShareViewModel.currentPlayer = validatedPlayer
                            navigateToMainMenu()
                        }
                    }

                }
            )
        ShogiButton(
            text = loginViewModel.getAlternativeMode(),
            onClick = {loginViewModel.toggleMode()}
        )
            Spacer(modifier = Modifier.weight(1f))
    }
}

