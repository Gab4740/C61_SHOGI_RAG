package com.example.c61_shogi_rag.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.c61_shogi_rag.R
import com.example.c61_shogi_rag.engine.piece.Piece
import com.example.c61_shogi_rag.engine.piece.Position
import com.example.c61_shogi_rag.ui.screens.game_screen.GameViewModel

val cellSize: Dp = 43.dp

@Composable
fun PieceImage(modifier: Modifier = Modifier, pieceModel: Int?) {
    Box(
        modifier = modifier
            .background(color = Color.Unspecified)
            .size(cellSize)
    ) {
        if(pieceModel != null) {
            Icon(
                painter = painterResource(pieceModel),
                contentDescription = pieceModel.toString(),
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
fun Cell(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(color = Color.White)
            .border(BorderStroke(1.dp, Color.Black))
            .size(cellSize)
    ) {
        Image(
            painter = painterResource(R.drawable.wood),
            contentDescription = "Image de la case",
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun BoardBackground(modifier: Modifier = Modifier, boardSize: Int = 9, onTap: (Position) -> Unit = {}) {
    Box(modifier = modifier) {
        Column {
            for (row in 0 until boardSize) {
                Row {
                    for (column in 0 until boardSize) {
                        val position = Position(row, column)
                        Cell(
                            modifier = Modifier.clickable { onTap(position) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BoardLayout(modifier: Modifier = Modifier, boardSize: Int = 9, gameViewModel: GameViewModel) {
    key(gameViewModel.isPlayerTurn) { // Forcer la récomposition
        Box(modifier = modifier) {
            Column {
                for (row in 0 until boardSize) {
                    Row {
                        for (column in 0 until boardSize) {
                            val position:Position = Position(row, column)
                            val piece: Int? = gameViewModel.game.getPieceDrawable(position)

                            PieceImage(pieceModel = piece)

                        }
                    }
                }
            }
        }
    }

}

@Composable
fun CapturedPieces(modifier: Modifier = Modifier, isClickable: Boolean = false) {
    val drawableList = listOf<Int>(
        R.drawable.pawn_0,
        R.drawable.lance_0,
        R.drawable.knight_0,
        R.drawable.silver_0,
        R.drawable.gold_0,
        R.drawable.bishop_0,
        R.drawable.rook_0
    )

    LazyRow(
        modifier = modifier
    ) {
        itemsIndexed(drawableList) { index ,pieceItem ->
            CapturedPiece(
                modifier = Modifier
                    .then(if (isClickable) Modifier.clickable {  } else Modifier),
                imageRes = pieceItem
            )
        }
    }
}

@Composable
fun CapturedPiece(modifier: Modifier = Modifier, imageRes: Int) {
    Column(
        modifier = modifier
            .width(55.dp)
    ) {
        Icon(
            painter = painterResource(imageRes),
            contentDescription = "id",
            tint = Color.Unspecified
        )

        CounterText(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value =  0
        )
    }
}