package com.example.c61_shogi_rag.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.c61_shogi_rag.engine.minimax.Difficulty

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

@Preview
@Composable
private fun Preview() {
    Row {
        DifficultyOption(isSelected = true)
        DifficultyOption(difficulty = Difficulty.Medium)
        DifficultyOption(difficulty = Difficulty.Hard)
    }

}
@Composable
fun DifficultyOption(modifier: Modifier = Modifier,
                     difficulty: Difficulty = Difficulty.Easy,
                     isSelected: Boolean = false,
                     fontFamily: FontFamily = japanWaveFontFamily,
                     fixedwidth: Dp = 70.dp
) {
    Text(
        modifier = modifier
            .then(if (isSelected)
                Modifier
                    .background(CherryLog)
            else Modifier)
            .border(width = 1.dp, color = Color(0xFF210f17))
            .padding(10.dp)
            .width(fixedwidth)
            .height(40.dp),
        textAlign = TextAlign.Center,
        text = difficulty.name,
        color = if (isSelected) Color.White else Color.Black,
        fontSize = 24.sp,
        fontFamily = fontFamily,

    )
}