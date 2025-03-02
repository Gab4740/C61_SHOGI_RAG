package com.example.c61_shogi_rag

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.c61_shogi_rag.ui.theme.GameTitle
import com.example.c61_shogi_rag.ui.theme.MenuBottom
import com.example.c61_shogi_rag.ui.theme.japanWaveFontFamily

@Composable
fun MainMenu(innerPaddingValues: PaddingValues , modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        GameTitle(name = "Shogi RAG!", Modifier.weight(2f))
        MenuBottom(
            name = "Play",
            fontFamily = japanWaveFontFamily,
            modifier = Modifier.weight(1f)

        ) {}
        MenuBottom(
            name = "History",
            fontFamily = japanWaveFontFamily,
            modifier = Modifier.weight(1f)

        ) {}
        Spacer(modifier = Modifier.weight(1f))

    }


}