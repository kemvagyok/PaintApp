package com.example.painting.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.painting.feature.paint_details.PaintDetailsScreen
import com.example.painting.feature.paint_list.PaintListScreen
import com.example.painting.feature.paint_searching.PaintSearchingScreen
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.ArtworkSearch.route) {
       composable(Screen.ArtworkSearch.route) {
           PaintSearchingScreen(navController, viewModel = hiltViewModel() )
       }
        composable(
            route = Screen.ArtworkList.route
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val author = backStackEntry.arguments?.getString("author") ?: ""
            val describing = backStackEntry.arguments?.getString("describing") ?: ""
            PaintListScreen(navController, viewModel = hiltViewModel(), title,author, describing )
        }
        composable(route = Screen.ArtworkDetails.route
        ) { backStackEntry ->
            val artworkId = backStackEntry.arguments?.getString("artworkId")?.toInt() ?: return@composable
            PaintDetailsScreen(
                navController, viewModel = hiltViewModel(), artworkId = artworkId
            )
        }
    }

}