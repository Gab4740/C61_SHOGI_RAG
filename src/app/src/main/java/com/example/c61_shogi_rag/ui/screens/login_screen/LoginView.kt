package com.example.c61_shogi_rag.ui.screens.login_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            Title(name = "Username")
        TextField(value = loginViewModel.username, onValueChange = {loginViewModel.username = it})
            Spacer(modifier = Modifier.weight(1f))
            ShogiButton(
                name = "Submit",
                onClick = {
                    //TODO une fois cliquer sur submit faire une methode
                    // qui fait attendre le temps que toutes les verifications sois terminer
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