package com.example.skillcinema.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.GalleryPreviewBinding
import com.example.skillcinema.entity.Image

class GalleryPagingAdapter : PagingDataAdapter<Image, GalleryPreviewViewHolder>(GalleryDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryPreviewViewHolder {
        val binding = GalleryPreviewBinding.inflate(LayoutInflater.from(parent.context))
        return GalleryPreviewViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GalleryPreviewViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            Glide.with(galleryImage.context)
                .load(item?.imageUrl)
                .placeholder(R.drawable.test_image_2)
                .into(galleryImage)
        }
    }
}
class GalleryDiffUtilCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
        oldItem.imageUrl == newItem.imageUrl

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
        oldItem == newItem
}