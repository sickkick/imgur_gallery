package com.example.imgurgallery.data.models

data class SearchResults(
    val `data`: List<Data>,
    val status: Int,
    val success: Boolean
)