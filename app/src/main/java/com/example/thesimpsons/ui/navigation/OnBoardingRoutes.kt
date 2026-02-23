package com.example.thesimpsons.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class OnBoardingRoutes: NavKey {
    @Serializable
    data object Welcome:OnBoardingRoutes()
    @Serializable
    data object DefineVisualMode:OnBoardingRoutes()
    @Serializable
    data object EnterName:OnBoardingRoutes()
}

