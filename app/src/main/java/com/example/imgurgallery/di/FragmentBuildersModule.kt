package com.example.imgurgallery.di

import com.example.imgurgallery.ui.imageSets.ImageSetsFragment
import com.example.imgurgallery.ui.search.SearchFragment
import com.example.imgurgallery.ui.singleImage.SingleImageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeImageSetsFragment(): ImageSetsFragment

    @ContributesAndroidInjector
    abstract fun contributeSingleImageFragment(): SingleImageFragment
}
