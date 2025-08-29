package com.miguelsjd.repofinder.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.miguelsjd.core.theme.RepoFinderTheme
import com.miguelsjd.navigation.Destinations
import com.miguelsjd.navigation.FeatureEntry
import org.koin.java.KoinJavaComponent.getKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RepoFinderTheme {
                AppNavHost()
            }
        }
    }
}

@Composable
fun AppNavHost() {
    val navController: NavHostController = rememberNavController()
    val entries: List<FeatureEntry> = getKoin().getAll()

    NavHost(
        navController = navController,
        startDestination = Destinations.Home.route
    ) {
        entries.forEach { entry ->
            with(entry) { registerGraph(navController) }
        }
    }
}