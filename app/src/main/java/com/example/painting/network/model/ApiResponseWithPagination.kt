package com.example.painting.network.model

data class ApiResponseWithPagination<T>(
    val pagination: Pagination,
    val data : T
)