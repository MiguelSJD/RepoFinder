package com.miguelsjd.navigation

sealed class Destinations(val route: String) {
    data object Home : Destinations("home")
    data object Detail : Destinations("detail/{repositoryId}") {
        fun createRoute(repositoryId: Int) = "detail/$repositoryId"
    }
}