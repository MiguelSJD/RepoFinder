package com.miguelsjd.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface FeatureEntry {
    fun NavGraphBuilder.registerGraph(navController: NavController)
}
