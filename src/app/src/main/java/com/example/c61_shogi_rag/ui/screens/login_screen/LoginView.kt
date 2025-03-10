package com.example.c61_shogi_rag.ui.screens.login_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c61_shogi_rag.ui.theme.ShogiButton

@Composable
fun LoginView(modifier: Modifier = Modifier, loginViewModel: LoginViewModel = viewModel(),
              navigateToMainMenu:(String) -> Unit) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "HOME SCREEN", fontSize = 25.sp)
            Spacer(modifier = Modifier.weight(1f))
            TextField(value = loginViewModel.connectedUser, onValueChange = {loginViewModel.connectedUser = it})
            Spacer(modifier = Modifier.weight(1f))
            ShogiButton(
                name = "Submit",
                onClick = {navigateToMainMenu(loginViewModel.connectedUser)}
            )
            Spacer(modifier = Modifier.weight(1f))
    }
}