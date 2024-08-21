package com.yaabelozerov.olympteam.presentation

sealed class Screens(val route: String) {
    object MainScreen : Screens("MainScreen")
    object LoginScreen: Screens("LoginScreen")
}
