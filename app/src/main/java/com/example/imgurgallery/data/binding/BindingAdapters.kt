package com.example.imgurgallery.data.binding

import android.R
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.util.*


@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("isVisible")
fun bindIsVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

/*
@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Picasso.get()
            .load(imageUrl)
            .into(view)
    } else {
        view.setImageDrawable(null)
    }
}

 */

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("videoView")
fun bindVideoFromUrl(videoView: VideoView, videoUrl: String?) {
    val uri: Uri = Uri.parse(videoUrl)
    videoView.setVideoURI(uri)
    //simpleVideoView.start()
}

@BindingAdapter("backGroundColor")
fun bindBackgroundColor(view: View, color: String?) {
    if(!color.isNullOrEmpty()) {
        view.setBackgroundColor(Color.parseColor(color))
    }
}

@BindingAdapter("imageBitmap")
fun bindImageBitmap(view: ImageView, bitmap: Bitmap?) {
    if(bitmap != null) {
        view.setImageBitmap(bitmap)
    }
}

@BindingAdapter("backGroundTintColor")
fun bindBackGroundTintColor(view: View, color: String?) {
    if(!color.isNullOrEmpty()) {
        view.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))
    }
}

@BindingAdapter("textColor")
fun bindTextColor(button: Button, color: String?) {
    if(!color.isNullOrEmpty()) {
        button.setTextColor(Color.parseColor(color))
    }
}

/*
@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
        view.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}
*/

@Suppress("DEPRECATION")
@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, source: String?) {
    if (!source.isNullOrEmpty()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.text = Html.fromHtml(source, Html.FROM_HTML_SEPARATOR_LINE_BREAK_DIV)
        } else {
            view.text = Html.fromHtml(source)
        }
    }
}

@BindingAdapter("android:onLongClick")
fun setOnLongClickListener(view: View, func: () -> Unit) {
    view.setOnLongClickListener {
        func()
        true
    }
}

@BindingAdapter("rgbButtonBackground")
fun setRgbButtonBackground(button: Button, color: String?) {
    val nColor = color!!.split(",")

    /*
    button.backgroundTintList = ColorStateList.valueOf(Color.rgb(
        nColor[0].toInt(),
        nColor[1].toInt(),
        nColor[2].toInt()
    ))
*/
    button.setBackgroundColor(
        Color.rgb(
            nColor[0].toInt(),
            nColor[1].toInt(),
            nColor[2].toInt()
        )
    )
}

