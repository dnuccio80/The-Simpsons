package com.example.thesimpsons.ui.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thesimpsons.ui.theme.DarkBackgroundApp
import com.example.thesimpsons.ui.theme.DarkText
import com.example.thesimpsons.ui.theme.LightBackgroundApp
import com.example.thesimpsons.ui.theme.LightText
import com.example.thesimpsons.ui.theme.YellowMain

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
fun ButtonContentText(text: String, color: Color = Color.White, modifier: Modifier = Modifier) {
    Text(
        text,
        modifier = modifier,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        color = color
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
fun Header(
    query: String,
    queryPlaceHolder:String,
    title:String,
    leadingImage: Painter,
    trailingImage: Painter,
    onQueryChange:(String) -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        Image(leadingImage, "", Modifier.size(80.dp))
        Spacer(Modifier.size(4.dp))
        TitleItem(title)
        Spacer(Modifier.size(4.dp))
        Image(trailingImage, "", Modifier.size(80.dp))
    }
    QuerySearchItem(
        query,
        queryPlaceHolder
    ) { value -> onQueryChange(value) }
}

@Composable
fun ButtonTextItem(text: String, contentColor:Color = Color.White, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = YellowMain
        ),
        contentPadding = PaddingValues(horizontal = 16.dp)

    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            ButtonContentText(text, contentColor)
            Icon(Icons.AutoMirrored.Default.ArrowForward, "", tint = contentColor)
        }
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

