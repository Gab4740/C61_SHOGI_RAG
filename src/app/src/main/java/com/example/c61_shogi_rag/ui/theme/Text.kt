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
fun Title(modifier: Modifier = Modifier, name: String) {
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

@Composable
fun CounterText(modifier: Modifier = Modifier, value: Any, fontSize : Int = 20) {
    Text(
        modifier = modifier,
        text = value.toString(),
        fontSize = fontSize.sp,
        fontFamily = japanWaveFontFamily
    )
}

@Composable
fun CustomText(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 20.sp,
        fontFamily = japanWaveFontFamily
    )
}

