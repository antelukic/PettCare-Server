package com.pettcare.core

data class PaginatedResponse<T>(
    val items: List<T>
)
