package com.example.imgurgallery.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.*
import com.example.imgurgallery.data.binding.bindImageFromUrl
import com.example.imgurgallery.data.models.Data
import com.example.imgurgallery.data.models.Image
import com.example.imgurgallery.data.models.ImageData
import com.example.imgurgallery.databinding.AlbumItemBinding
import kotlinx.android.synthetic.main.album_item.view.*

class SearchAdapter : ListAdapter<Data, SearchAdapter.ViewHolder>(DiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        if(item.images[0].type == "image/jpeg") {
            bindImageFromUrl(holder.itemView.albumImage, item.images[0].link)
        } else {
            bindImageFromUrl(holder.itemView.albumImage, "https://i.imgur.com/${item.images[0].id}b.jpg")
        }

        holder.apply {
            bind(createOnClickListener(item.images), item, isGridLayoutManager())
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AlbumItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    private fun createOnClickListener(images: List<Image>): View.OnClickListener {
        return View.OnClickListener {
            val imageData: MutableList<ImageData> = ArrayList()

            images.forEach { image ->
                imageData.add(
                    ImageData(
                            description = image.description,
                            gifv = image.gifv,
                            has_sound = image.has_sound,
                            height = image.height,
                            hls = image.hls,
                            id = image.id,
                            link = image.link,
                            mp4 = image.mp4,
                            mp4_size = image.mp4_size,
                            title = image.title,
                            type = image.type
                    )
                )
            }

            val direction = SearchFragmentDirections.actionSearchFragmentToImageSetsFragment(imageData.toTypedArray())
            it.findNavController().navigate(direction)
        }
    }

    private fun isGridLayoutManager() = recyclerView.layoutManager is GridLayoutManager

    class ViewHolder(
        private val binding: AlbumItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Data, isGridLayoutManager: Boolean) {
            binding.apply {
                clickListener = listener
                albumItem = item
                title.visibility = if (isGridLayoutManager) View.GONE else View.VISIBLE
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Data>() {

    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }
}