package com.example.c61_shogi_rag.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.c61_shogi_rag.R
import com.example.c61_shogi_rag.engine.piece.Piece
import com.example.c61_shogi_rag.engine.piece.Position
import com.example.c61_shogi_rag.ui.screens.game_screen.GameViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

val cellSize: Dp = 43.dp


@Composable
fun ShogiPiece(modifier: Modifier = Modifier,
               pieceResID: Int? = null,
               opposite: Boolean? = false,
               onCellClick: () -> Unit = {},
               onPieceClick: () -> Unit = {}) {

    if (pieceResID == null) {
        Box(
            modifier = modifier
                .padding(1.dp)
                .background(Color.Transparent)
        )
    }
    else {
        Image(
            painter = painterResource(id = pieceResID),
            contentDescription = "Image de la pièce",
            modifier = modifier
                .padding(1.dp)
                .then(if (opposite == true) Modifier.rotate(180F) else Modifier)

        )
    }
}

@Composable
fun ShogiboardCell(modifier: Modifier = Modifier,
                   position: Position,
                   onCellClick: () -> Unit = {},
                   shogiPiece: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .size(41.dp)
            .border(BorderStroke(1.dp, Color.Black))
            .clickable(onClick = onCellClick)
    ) {
        Image(
            painter = painterResource(R.drawable.wood),
            contentDescription = "Image de la case",
            contentScale = ContentScale.Crop,
        )

        shogiPiece()
    }
}

@Composable
fun Shogiboard(modifier: Modifier = Modifier, boardSize: Int = 9) {
    var piecePosition by remember { mutableStateOf(
        Position(
            1,
            1
        )
    ) }
    Box {
        Column {
            for (row in 0 until boardSize) {
                Row {
                    for (column in 0 until boardSize) {
                        if(column == piecePosition.posX && row == piecePosition.posY) {
                            ShogiboardCell(
                                position = Position(
                                    column,
                                    row
                                ),
                                onCellClick = {piecePosition =
                                    Position(
                                        column,
                                        row
                                    )
                                },

                                shogiPiece = {
                                    ShogiPiece(
                                        pieceResID = R.drawable.p_pawn_0,
                                        opposite = false,
                                    )
                                }
                            )
                        }
                        else {
                            ShogiboardCell(
                                position = Position(
                                    column,
                                    row
                                ),
                                onCellClick = {piecePosition =
                                    Position(
                                        column,
                                        row
                                    )
                                },
                                shogiPiece = {
                                    ShogiPiece(
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PieceImage(modifier: Modifier = Modifier, pieceModel: Piece?) {
    Box(
        modifier = modifier
            .background(color = Color.Unspecified)
            .size(cellSize)
    ) {
        if(pieceModel != null) {
            Icon(
                painter = painterResource(pieceModel.imagE_ID),
                contentDescription = pieceModel.id.toString(),
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
                        val piece: Piece? = gameViewModel.game.getPieceAt(position)

                        PieceImage(pieceModel = piece)

                    }
                }
            }
        }
    }
}

@Composable
fun CapturedPieces(modifier: Modifier = Modifier) {
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
        items(drawableList) { pieceItem ->
            CapturedPiece(imageRes = pieceItem)
        }
    }
}

@Composable
fun CapturedPiece(modifier: Modifier = Modifier, imageRes: Int) {
    Column(
        Modifier
            .width(55.dp)
            .clickable {  }
    ) {
        Icon(
            painter = painterResource(imageRes),
            contentDescription = "id",
            tint = Color.Unspecified
        )

        CounterText(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            counter =  0
        )
    }
}