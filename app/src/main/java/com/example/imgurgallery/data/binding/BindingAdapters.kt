package com.example.imgurgallery.data.binding

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.imgurgallery.ui.imageSets.PlayerStateCallback
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

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

@BindingAdapter("video_url", "on_state_change")
fun PlayerView.loadVideo(url: String, callback: PlayerStateCallback) {
    if (url == null) return

    val player = ExoPlayerFactory.newSimpleInstance(
        context, DefaultRenderersFactory(context), DefaultTrackSelector(),
        DefaultLoadControl()
    )

    player.playWhenReady = true
    player.repeatMode = Player.REPEAT_MODE_OFF
    // When changing track, retain the latest frame instead of showing a black screen
    setKeepContentOnPlayerReset(true)
    // We'll show the controller
    this.useController = true
    // Provide url to load the video from here
    val mediaSource = ExtractorMediaSource.Factory(
        DefaultHttpDataSourceFactory("Demo")
    ).createMediaSource(Uri.parse(url))

    player.prepare(mediaSource)

    this.player = player

    this.player!!.addListener(object : Player.EventListener {

        override fun onPlayerError(error: ExoPlaybackException) {
            super.onPlayerError(error)
            Toast.makeText(context,"Oops! Error occurred while playing media.", Toast.LENGTH_SHORT).show()
            //context("Oops! Error occurred while playing media.")
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            super.onPlayerStateChanged(playWhenReady, playbackState)

            if (playbackState == Player.STATE_BUFFERING) callback.onVideoBuffering(player) // Buffering.. set progress bar visible here
            if (playbackState == Player.STATE_READY){
                // [PlayerView] has fetched the video duration so this is the block to hide the buffering progress bar
                callback.onVideoDurationRetrieved(player.duration, player)
            }
            if (playbackState == Player.STATE_READY && player.playWhenReady){
                // [PlayerView] has started playing/resumed the video
                callback.onStartedPlaying(player)
            }
        }
    })
}

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
    button.setBackgroundColor(
        Color.rgb(
            nColor[0].toInt(),
            nColor[1].toInt(),
            nColor[2].toInt()
        )
    )
}

