package com.example.c61_shogi_rag.ui.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun GameTitle(name: String, modifier: Modifier) {
    Text(
        text = name,
        fontSize = 50.sp,
        fontFamily = asianaFontFamily,
        modifier = modifier
    )
}

