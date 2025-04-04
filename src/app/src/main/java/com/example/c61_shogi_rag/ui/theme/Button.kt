package com.example.c61_shogi_rag.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShogiButton(modifier: Modifier = Modifier,
                text: String,
                fontFamily: FontFamily = japanWaveFontFamily,
                enabled:Boolean = true,
                onClick: () -> Unit = {}) {
    Button(
        onClick = { onClick() },
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = CherryLog
        ),
        modifier = modifier
            .requiredWidth(200.dp)
            .padding(10.dp),
        enabled = enabled

    ) {
        Text(
            text = text,
            fontSize = 30.sp,
            fontFamily = fontFamily
        )
    }
}