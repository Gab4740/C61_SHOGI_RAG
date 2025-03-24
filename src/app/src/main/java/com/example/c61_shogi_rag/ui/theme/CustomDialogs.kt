package com.example.c61_shogi_rag.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.c61_shogi_rag.R
import com.example.c61_shogi_rag.ui.theme.Cherry2





@Composable
fun SenteComposable(modifier: Modifier = Modifier, onClick:() -> Unit = {} ) {
        Icon(
            modifier = Modifier
                .size(60.dp)
                .clickable { onClick() },
            painter = painterResource(R.drawable.sente_king_0),
            contentDescription = "Image Sente",
            tint = Color.Unspecified,
            )
}

@Composable
fun GoteComposable(modifier: Modifier = Modifier,  onClick:() -> Unit = {}) {
    Icon(
        modifier = modifier
            .size(60.dp)
            .clickable { onClick() },
        painter = painterResource(R.drawable.gote_king_0),
        contentDescription = "Image Sente",
        tint = Color.Unspecified
    )
}

@Composable
fun GoteSenteComposable(modifier: Modifier = Modifier,  onClick:() -> Unit = {}) {
    Icon(
        modifier = modifier
            .size(60.dp)
            .clickable { onClick() },
        painter = painterResource(R.drawable.gote_sente),
        contentDescription = "Image Sente",
        tint = Color.Unspecified
    )
}
