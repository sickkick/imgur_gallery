package com.example.imgurgallery.ui.imageSets

import androidx.lifecycle.ViewModel
import com.example.imgurgallery.data.search.SearchRepository
import javax.inject.Inject

class ImageSetsViewModel @Inject constructor(private val repository: SearchRepository)  : ViewModel() {
}