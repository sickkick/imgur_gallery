package com.example.imgurgallery.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imgurgallery.ui.imageSets.ImageSetsViewModel
import com.example.imgurgallery.ui.search.SearchViewModel
import com.example.imgurgallery.ui.singleImage.SingleImageViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ImageSetsViewModel::class)
    abstract fun bindImageSetsViewModel(viewModel: ImageSetsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SingleImageViewModel::class)
    abstract fun bindSingleImageViewModel(viewModel: SingleImageViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
