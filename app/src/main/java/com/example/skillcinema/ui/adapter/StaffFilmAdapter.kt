package com.example.skillcinema.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.GalleryPreviewBinding
import com.example.skillcinema.databinding.StaffFilmBinding
import com.example.skillcinema.entity.Image
import com.example.skillcinema.entity.StaffFilm

class StaffFilmAdapter(val size: Int? = null) : RecyclerView.Adapter<StaffFilmViewHolder>() {

    private var values: List<StaffFilm> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<StaffFilm>) {
        val list = data.sortedBy { it.rating }.reversed()
        this.values = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffFilmViewHolder {
        val binding = StaffFilmBinding.inflate(LayoutInflater.from(parent.context))
        return StaffFilmViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StaffFilmViewHolder, position: Int) {
        val item = values.getOrNull(position)
        val name = item?.nameRu ?: item?.nameEn
        val rating = if (item?.rating != null) ", ${item.rating}" else ""
        holder.binding.textStaffFilm.text = name + rating
        }

    override fun getItemCount(): Int = if (size == null || size > values.size) values.size else size
}

class StaffFilmViewHolder(val binding: StaffFilmBinding) : RecyclerView.ViewHolder(binding.root)