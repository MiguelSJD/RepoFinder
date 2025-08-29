package com.miguelsjd.details.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.miguelsjd.details.presentation.view.DetailsScreen
import com.miguelsjd.navigation.Destinations
import com.miguelsjd.navigation.FeatureEntry

internal class DetailsFeatureEntry : FeatureEntry {
    override fun NavGraphBuilder.registerGraph(navController: NavController) {
        composable(
            route = Destinations.Detail.route,
            arguments = listOf(
                navArgument("repositoryId") { type = NavType.IntType }
            )
        ) { stackEntry ->
            val repositoryId = stackEntry.arguments?.getInt("repositoryId")
                ?: error("Repository id is required")

            DetailsScreen(
                repositoryId = repositoryId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
