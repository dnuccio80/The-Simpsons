package com.example.thesimpsons.ui.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleItem(text:String, color: Color = Color.White) {
    Text(
        text,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
        color = color
    )
}

@Composable
fun SubtitleItem(text:String, color: Color = Color.White, modifier: Modifier = Modifier) {
    Text(
        text,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = color
    )
}

@Composable
fun BodyTextItem(text:String, color: Color = Color.White) {
    Text(
        text,
        fontSize = 14.sp,
        color = color
    )
}

@Composable
fun InfoTextItem(concept:String, description:String, color: Color = Color.White) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            concept,
            fontSize = 16.sp,
            color = color,
            fontWeight = FontWeight.ExtraBold,
        )
        Text(
            description,
            fontSize = 14.sp,
            color = color
        )

    }

}

