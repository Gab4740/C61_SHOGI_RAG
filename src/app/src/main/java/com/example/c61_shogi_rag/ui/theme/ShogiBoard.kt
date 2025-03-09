package com.example.c61_shogi_rag.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.c61_shogi_rag.R
import com.example.c61_shogi_rag.engine.game.Board
import com.example.c61_shogi_rag.engine.piece.Position


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
fun Shogiboard(modifier: Modifier = Modifier, board: Board) {
    var board by remember { mutableStateOf(board) }
    var piecePosition by remember { mutableStateOf(
        Position(
            1,
            1
        )
    ) }

    Column {
        for (row in 0 until board.boarD_SIZE) {
            Row {
                for (column in 0 until board.boarD_SIZE) {
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
                                    pieceResID = R.drawable._fu_1,
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


