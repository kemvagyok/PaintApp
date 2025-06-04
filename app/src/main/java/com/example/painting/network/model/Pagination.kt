package com.example.painting.network.model

data class Pagination(
    val total : Int,
    val limit : Int,
    val offset : Int,
    val total_pages: Int,
    val current_page: Int,
    val next_url : String?
)