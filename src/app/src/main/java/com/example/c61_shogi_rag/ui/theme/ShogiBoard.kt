package com.example.c61_shogi_rag.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.c61_shogi_rag.R


@Composable
fun ShogiPiece(modifier: Modifier = Modifier, pieceResID: Int? = null, opposite: Boolean = false,) {
    if(pieceResID != null) {
        Image(
            painter = painterResource(id = pieceResID),
            contentDescription = "Image de la pièce",
            modifier = modifier
                .fillMaxSize()
                .then(if (opposite) Modifier.rotate(180F) else Modifier)
        )
    }
}

@Composable
fun ShogiboardCell(pieceResID: Int? = null, opposite: Boolean = false) {
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .size(42.dp)
            .border(BorderStroke(1.dp, Color.Black))
    ) {
        Image(
            painter = painterResource(R.drawable.wood),
            contentDescription = "Image de la case",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        ShogiPiece(Modifier.align(Alignment.Center), R.drawable._fu_1, opposite)

    }

}

@Composable
fun Shogiboard(boardSize: Int, modifier: Modifier = Modifier) {
    Column {
        for (row in 0 until boardSize) {
            Row {
                for (column in 0 until boardSize) {
                    ShogiboardCell(
                        R.drawable._fu_1,
                        true
                    )
                }
            }
        }
    }
}
