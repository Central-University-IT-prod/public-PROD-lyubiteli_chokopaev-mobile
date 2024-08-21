package com.yaabelozerov.olympteam.presentation

import androidx.compose.runtime.Composable

sealed class Tabs(val route: String, val title: String) {
    object FeedScreen : Tabs("FeedScreen", "Feed") {
    }
    object ProfileScreen: Tabs("ProfileScreen", "Profile")
}
