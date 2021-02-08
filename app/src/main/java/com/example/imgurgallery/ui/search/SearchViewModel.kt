package com.example.imgurgallery.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imgurgallery.data.api.Result
import com.example.imgurgallery.data.models.SearchResults
import com.example.imgurgallery.data.search.SearchRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {

    //val searchReults = MutableLiveData<Result<SearchResults>>()

    val searchReults: MutableLiveData<Result<SearchResults>> by lazy {
        MutableLiveData<Result<SearchResults>>()
    }

    fun getSearchResults(search: String) {
        viewModelScope.launch {
             searchReults.value = repository.searchResults(search)
        }
    }
}