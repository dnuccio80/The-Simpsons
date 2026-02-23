package com.example.thesimpsons.ui.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.thesimpsons.R
import com.example.thesimpsons.ui.core.BodyTextItem
import com.example.thesimpsons.ui.core.ButtonTextItem
import com.example.thesimpsons.ui.core.SubtitleItem
import com.example.thesimpsons.ui.core.TitleItem
import com.example.thesimpsons.ui.core.extensions.back
import com.example.thesimpsons.ui.core.extensions.navigateTo
import com.example.thesimpsons.ui.navigation.OnBoardingRoutes
import com.example.thesimpsons.ui.navigation.OnBoardingRoutes.*
import com.example.thesimpsons.ui.theme.DarkBackgroundApp
import com.example.thesimpsons.ui.theme.LightBackgroundApp
import com.example.thesimpsons.ui.theme.YellowMain
import com.example.thesimpsons.ui.theme.YellowSecondary

@Composable
fun OnBoardingScreen(innerPadding: PaddingValues, onNavigateToApp: () -> Unit) {

    val backgroundApp = if (isSystemInDarkTheme()) DarkBackgroundApp else LightBackgroundApp

    val backStack = rememberNavBackStack(Welcome)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(backgroundApp),
        contentAlignment = Alignment.Center
    ) {
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.back() },
            entryProvider = entryProvider {
                entry<Welcome> { Welcome { backStack.navigateTo(DefineVisualMode) } }
                entry<DefineVisualMode> { DefineVisualMode { backStack.navigateTo(EnterName) } }
                entry<EnterName> { EnterName { onNavigateToApp() } }
            },
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            popTransitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                )
            }
        )
    }

}

@Composable
fun Welcome(onNextClick: () -> Unit) {
    OnBoardingBody(
        painterResource(R.drawable.family_friendly),
        buttonText = "Next",
        content = {
            TitleItem("Welcome to")
            Image(painterResource(R.drawable.logo), contentDescription = "")
            TitleItem("API")
        }
    ) { onNextClick() }
}

@Composable
fun DefineVisualMode(onNextClick: () -> Unit) {
    OnBoardingBody(
        image = painterResource(R.drawable.family),
        buttonText = "Next",
        content = {
            SubtitleItem("Let´s define your favorite mode")
            VisualModeRadioButtonsContainer()
        }
    ) { onNextClick() }
}

@Composable
fun EnterName(onNextClick: () -> Unit) {

    var name by rememberSaveable { mutableStateOf("") }
    var showError by rememberSaveable { mutableStateOf(false) }

    OnBoardingBody(
        image = painterResource(R.drawable.ralph_ic),
        buttonText = "Start",
        content = {
            SubtitleItem("Now, tell Ralph your name")
            Spacer(Modifier.size(16.dp))
            GenericTextField(
                query = name,
                placeholder = "Your name..",
                onUpdate = { name = it }
            )
            AnimatedVisibility(showError) {
                Text("You must to enter your name!", color = Color.Red)
            }
        }
    ) {
        if (name.isNotBlank()) onNextClick()
        else showError = true
    }
}

@Composable
fun GenericTextField(
    query: String,
    placeholder: String,
    onUpdate: (String) -> Unit,
) {
    TextField(
        value = query,
        onValueChange = {
            val newValue = if (it.isNotBlank()) {
                it.replaceFirstChar { char -> char.titlecase() }
            } else it
            onUpdate(newValue)
        },
        singleLine = true,
        shape = RoundedCornerShape(4.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedContainerColor = YellowMain,
            unfocusedContainerColor = YellowMain,
            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedPlaceholderColor = Color.Black,
            focusedPlaceholderColor = Color.Black
        ),
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
private fun OnBoardingBody(
    image: Painter,
    buttonText: String,
    content: @Composable () -> Unit,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            image,
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
            content()
        }
        ButtonTextItem(buttonText, contentColor = Color.Black) { onButtonClick() }
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
            RadioButtonItem(it, selected) { newValue -> selected = newValue }
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