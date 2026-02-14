package com.example.thesimpsons.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thesimpsons.ui.theme.DarkBackgroundApp
import com.example.thesimpsons.ui.theme.LightBackgroundApp

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

@Composable
fun ScreenContainer(innerPadding:PaddingValues, content:@Composable () -> Unit) {

    val backgroundScreen = if (isSystemInDarkTheme()) DarkBackgroundApp else LightBackgroundApp

    Box(
        Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(backgroundScreen), contentAlignment = Alignment.Center
    ) {
        content()
    }
}

