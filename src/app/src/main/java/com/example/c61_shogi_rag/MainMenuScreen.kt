package com.example.c61_shogi_rag

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.c61_shogi_rag.ui.theme.GameTitle
import com.example.c61_shogi_rag.ui.theme.MenuButton
import com.example.c61_shogi_rag.ui.theme.japanWaveFontFamily

@Composable
fun MainMenuScreen(innerPaddingValues: PaddingValues, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(innerPaddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        GameTitle(name = "Shogi RAG!", Modifier.weight(2f))
        MenuButton(
            name = "Play",
            fontFamily = japanWaveFontFamily,
            modifier = Modifier.weight(1f)

        ) {}
        MenuButton(
            name = "History",
            fontFamily = japanWaveFontFamily,
            modifier = Modifier.weight(1f)

        ) {}
        Spacer(modifier = Modifier.weight(1f))

    }


}