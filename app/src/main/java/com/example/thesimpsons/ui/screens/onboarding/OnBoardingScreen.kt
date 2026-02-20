package com.example.thesimpsons.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.thesimpsons.R
import com.example.thesimpsons.ui.core.BodyTextItem
import com.example.thesimpsons.ui.core.ButtonTextItem
import com.example.thesimpsons.ui.core.SubtitleItem
import com.example.thesimpsons.ui.core.TitleItem
import com.example.thesimpsons.ui.theme.DarkBackgroundApp
import com.example.thesimpsons.ui.theme.LightBackgroundApp
import com.example.thesimpsons.ui.theme.YellowMain
import com.example.thesimpsons.ui.theme.YellowSecondary

@Composable
fun OnBoardingScreen(innerPadding: PaddingValues) {

    val backgroundApp = if (isSystemInDarkTheme()) DarkBackgroundApp else LightBackgroundApp
    val boardingNavController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(backgroundApp),
        contentAlignment = Alignment.Center
    ) {
        DefineVisualMode()
    }

}

@Composable
fun Welcome() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painterResource(R.drawable.family_friendly),
            contentDescription = "",
            modifier = Modifier
                .size(400.dp),
            contentScale = ContentScale.FillHeight
        )
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleItem("Welcome to")
            Image(painterResource(R.drawable.logo), contentDescription = "")
            TitleItem("API")
        }
        ButtonTextItem("Next", contentColor = Color.Black) { }
    }
}

@Composable
fun DefineVisualMode() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painterResource(R.drawable.family),
            contentDescription = "",
            modifier = Modifier
                .size(400.dp),
            contentScale = ContentScale.FillHeight
        )
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubtitleItem("Let´s define your favorite mode")
            VisualModeRadioButtonsContainer()
        }
        ButtonTextItem("Next", contentColor = Color.Black) { }
    }
}

@Composable
fun VisualModeRadioButtonsContainer() {

    val radioItems = listOf(
        "Dark Mode",
        "Light Mode",
    )

    var selected by rememberSaveable { mutableStateOf(radioItems.first()) }

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        radioItems.forEach {
            RadioButtonItem(it, selected) {newValue -> selected = newValue }
        }
    }
}

@Composable
fun RadioButtonItem(text: String, selected: String, onClick: (String) -> Unit) {
    Row(
        Modifier
            .clickable { onClick(text) },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected == text,
            onClick = { },
            colors = RadioButtonDefaults.colors(
                selectedColor = YellowMain,
                unselectedColor = YellowSecondary
            )
        )
        BodyTextItem(text)
    }
}