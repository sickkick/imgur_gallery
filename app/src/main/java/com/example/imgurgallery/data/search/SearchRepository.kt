package com.example.imgurgallery.data.search

import com.example.imgurgallery.data.api.ApiService
import com.example.imgurgallery.data.api.BaseDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(private val service: ApiService): BaseDataSource() {
    suspend fun searchResults(search: String) = getResult { service.albumSearch(search, "album") }
}