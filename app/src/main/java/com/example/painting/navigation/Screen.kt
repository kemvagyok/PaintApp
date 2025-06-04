package com.example.painting.navigation

sealed class Screen(val route: String) {
    data object ArtworkSearch : Screen("artworkSearching")
    data object ArtworkList : Screen("artworkList/title={title}&author={author}&describing={describing}") {
        fun createRoute(title: String, author: String, describing: String) = "artworkList/title=$title&author=$author&describing=$describing"
    }
    data object ArtworkDetails : Screen("artworkDetails/{artworkId}") {
        fun createRoute(artworkId: Int) = "artworkDetails/$artworkId"
    }
}