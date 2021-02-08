package com.example.imgurgallery.data.api

import com.example.imgurgallery.data.models.SearchResults
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    //Imgur search
    @GET("search")
    suspend fun albumSearch(
        @Query("q_all") q_all: String,
        @Query("q_type") q_type: String): Response<SearchResults>
}