package com.example.thesimpsons.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thesimpsons.ui.theme.DarkBackgroundApp
import com.example.thesimpsons.ui.theme.DarkText
import com.example.thesimpsons.ui.theme.LightBackgroundApp
import com.example.thesimpsons.ui.theme.LightText

@Composable
fun TitleItem(text: String, whiteColor: Boolean = false) {

    val color = if (isSystemInDarkTheme()) DarkText else LightText

    Text(
        text,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        color = if (whiteColor) Color.White else color
    )
}

@Composable
fun SubtitleItem(text: String, whiteColor: Boolean = false, modifier: Modifier = Modifier) {

    val color = if (isSystemInDarkTheme()) DarkText else LightText

    Text(
        text,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = if (whiteColor) Color.White else color,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun BodyTextItem(text: String, whiteColor: Boolean = false) {

    val color = if (isSystemInDarkTheme()) DarkText else LightText

    Text(
        text,
        fontSize = 14.sp,
        color = if (whiteColor) Color.White else color
    )
}

@Composable
fun InfoTextItem(concept: String, description: String, whiteColor: Boolean = false) {

    val color = if (isSystemInDarkTheme()) DarkText else LightText

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            concept,
            fontSize = 16.sp,
            color = if (whiteColor) Color.White else color,
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
fun QuerySearchItem(
    query: String,
    placeholder:String,
    onUpdate: (String) -> Unit,
) {
    TextField(
        value = query,
        onValueChange = {
            val newValue = if(it.isNotBlank()) {
                it.replaceFirstChar { char -> char.titlecase() }
            } else it
            onUpdate(newValue)
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        placeholder = { Text(placeholder) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "") },
        trailingIcon = {
            if (query.isNotBlank()) Icon(
                Icons.Default.Close,
                contentDescription = "",
                modifier = Modifier.clickable { onUpdate("") })
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    )
}

@Composable
fun ScreenContainer(innerPadding: PaddingValues, alignment:Alignment = Alignment.Center,  content: @Composable () -> Unit) {
    val backgroundScreen = if (isSystemInDarkTheme()) DarkBackgroundApp else LightBackgroundApp
    Box(
        Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(backgroundScreen), contentAlignment = alignment
    ) {
        content()
    }
}

