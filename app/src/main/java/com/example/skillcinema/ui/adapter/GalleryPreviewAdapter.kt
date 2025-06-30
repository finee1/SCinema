package com.example.skillcinema.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ActorPreviewBinding
import com.example.skillcinema.databinding.GalleryPreviewBinding
import com.example.skillcinema.entity.Image
import com.example.skillcinema.entity.StaffListItem

class GalleryPreviewAdapter : RecyclerView.Adapter<GalleryPreviewViewHolder>() {

    private var values: List<Image> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Image>) {
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryPreviewViewHolder {
        val binding = GalleryPreviewBinding.inflate(LayoutInflater.from(parent.context))
        return GalleryPreviewViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GalleryPreviewViewHolder, position: Int) {
        val item = values.getOrNull(position)

        with(holder.binding) {
            Glide.with(galleryImage.context)
                .load(item?.imageUrl)
                .placeholder(R.drawable.test_image_2)
                .into(galleryImage)
        }
    }

    override fun getItemCount(): Int = values.size
}

class GalleryPreviewViewHolder(val binding: GalleryPreviewBinding) : RecyclerView.ViewHolder(binding.root)