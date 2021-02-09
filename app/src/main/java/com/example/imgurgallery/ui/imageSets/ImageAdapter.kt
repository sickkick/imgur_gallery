package com.example.imgurgallery.ui.imageSets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Constraints
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imgurgallery.data.binding.bindImageFromUrl
import com.example.imgurgallery.data.binding.bindVideoFromUrl
import com.example.imgurgallery.data.models.Data
import com.example.imgurgallery.data.models.Image
import com.example.imgurgallery.data.models.ImageData
import com.example.imgurgallery.databinding.AlbumItemBinding
import com.example.imgurgallery.databinding.GalleryItemBinding
import com.example.imgurgallery.ui.search.SearchAdapter
import com.example.imgurgallery.ui.search.SearchFragmentDirections
import com.google.android.exoplayer2.Player
import kotlinx.android.synthetic.main.album_item.view.*
import kotlinx.android.synthetic.main.album_item.view.albumImage
import kotlinx.android.synthetic.main.gallery_item.view.*

class ImageAdapter : ListAdapter<ImageData, ImageAdapter.ViewHolder>(DiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        if(item.type == "image/jpeg") {
            holder.itemView.videoView.visibility = View.GONE
            holder.itemView.albumImage.visibility = View.VISIBLE

            bindImageFromUrl(holder.itemView.albumImage, item.link)

            val lp = Constraints.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )

            holder.itemView.layoutParams = lp
        } else {
            holder.itemView.videoView.visibility = View.VISIBLE
            holder.itemView.albumImage.visibility = View.GONE

            val lp = Constraints.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )

            holder.itemView.layoutParams = lp
        }

        holder.apply {
            bind(createOnClickListener(item), item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(GalleryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    private fun createOnClickListener(item: ImageData): View.OnClickListener {
        return View.OnClickListener {
            val direction = ImageSetsFragmentDirections.actionImageSetsFragmentToSingleImageFragment(item)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
            private val binding: GalleryItemBinding
    ) : RecyclerView.ViewHolder(binding.root), PlayerStateCallback {

        fun bind(listener: View.OnClickListener, item: ImageData) {
            binding.apply {
                clickListener = listener
                imageItem = item
                callback = this@ViewHolder
                title.visibility = View.GONE
                executePendingBindings()
            }
        }

        override fun onVideoDurationRetrieved(duration: Long, player: Player) {
        }

        override fun onVideoBuffering(player: Player) {
        }

        override fun onStartedPlaying(player: Player) {
        }

        override fun onFinishedPlaying(player: Player) {
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<ImageData>() {

    override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
        return oldItem == newItem
    }
}