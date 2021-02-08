package com.example.imgurgallery.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class AdConfig(
    val highRiskFlags: List<Any>,
    val safeFlags: List<String>,
    val showsAds: Boolean,
    val unsafeFlags: List<String>,
    val wallUnsafeFlags: List<Any>
)