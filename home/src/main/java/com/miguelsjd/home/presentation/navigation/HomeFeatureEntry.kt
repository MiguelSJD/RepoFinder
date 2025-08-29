package com.miguelsjd.home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.miguelsjd.home.presentation.view.HomeScreen
import com.miguelsjd.navigation.Destinations
import com.miguelsjd.navigation.FeatureEntry

internal class HomeFeatureEntry : FeatureEntry {

    override fun NavGraphBuilder.registerGraph(navController: NavController) {
        composable(route = Destinations.Home.route) {
            HomeScreen(
                onRepositoryClick = { repositoryId ->
                    navController.navigate(Destinations.Detail.createRoute(repositoryId))
                }
            )
        }
    }
}