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
import com.example.c61_shogi_rag.engine.piece.Position
import com.example.c61_shogi_rag.engine.piece.ShogiPiece
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Charriot
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Chevalier
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Fou
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.GeneralArgent
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.GeneralOr
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Lance
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Pion
import com.example.c61_shogi_rag.ui.screens.game_screen.GameViewModel

val cellSize: Dp = 43.dp

val drawableList = listOf<Int>(
    R.drawable.pawn_0,
    R.drawable.lance_0,
    R.drawable.knight_0,
    R.drawable.silver_0,
    R.drawable.gold_0,
    R.drawable.bishop_0,
    R.drawable.rook_0
)


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

@Composable
fun CapturedPieces(modifier: Modifier = Modifier, isClickable: Boolean = false) {

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
fun CapturedPieces(modifier: Modifier = Modifier,
                   hashMapCapturedPieces: HashMap<String, Int>,
                   isClickable: Boolean = false,
                   onClick:(shogiPiece:String) -> Unit = {}) {
    val keys = hashMapCapturedPieces.keys.toList()
    LazyRow(
        modifier = modifier
    ) {
        items(keys.size) { index: Int ->
            var imageRes: Int = -1
            var counter: Int = 0
            when(keys[index]) {
                Pion::class.java.canonicalName -> imageRes = R.drawable.pawn_0
                Lance::class.java.canonicalName -> imageRes = R.drawable.lance_0
                Chevalier::class.java.canonicalName -> imageRes = R.drawable.knight_0
                GeneralArgent::class.java.canonicalName -> imageRes = R.drawable.silver_0
                GeneralOr::class.java.canonicalName -> imageRes = R.drawable.gold_0
                Fou::class.java.canonicalName -> imageRes = R.drawable.bishop_0
                Charriot::class.java.canonicalName -> imageRes = R.drawable.rook_0
            }

            if (imageRes >= 0) {
                counter = hashMapCapturedPieces[keys[index]] ?: 0
                val canonicalName:String = keys[index]
                CapturedPiece(
                    modifier = Modifier
                        .then(if (isClickable) Modifier.clickable {onClick(canonicalName)} else Modifier),
                    imageRes = imageRes,
                    value = counter
                )
            }

        }
    }
}

@Composable
fun CapturedPiece(modifier: Modifier = Modifier, imageRes: Int, value: Int = 0) {
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
            value =  value
        )
    }
}