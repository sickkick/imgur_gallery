package com.example.imgurgallery.data.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageData(
    val description: String? = "",
    val gifv: String? = "",
    val has_sound: Boolean? = false,
    val height: Int? = 0,
    val hls: String? = "",
    val id: String? = "",
    val link: String? = "",
    val mp4: String? = "",
    val mp4_size: Int? = 0,
    val title: String? = "",
    val type: String? = ""
): Parcelable