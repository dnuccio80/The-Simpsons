package com.example.thesimpsons.ui.navigation

sealed class OnBoardingRoutes(val route:String) {
    data object Welcome:OnBoardingRoutes("welcome")
    data object DefineVisualMode:OnBoardingRoutes("defineVisualMode")
    data object EnterName:OnBoardingRoutes("enterName")
}