package com.example.c61_shogi_rag.ui.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun GameTitle(modifier: Modifier = Modifier,
              name: String) {
    Text(
        modifier = modifier,
        text = name,
        fontSize = 50.sp,
        fontFamily = asianaFontFamily,
    )
}

@Composable
fun PlayerTag(modifier: Modifier = Modifier, playerName:String) {
    Text(
        modifier = modifier,
        text = playerName,
        fontSize = 30.sp,
        fontFamily = japanWaveFontFamily
    )
}
