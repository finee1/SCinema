package com.example.skillcinema.ui.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ActorPreviewBinding
import com.example.skillcinema.databinding.FilmPreviewBinding
import com.example.skillcinema.entity.Premier
import com.example.skillcinema.entity.StaffListItem

class StaffAdapter(
    private val onClick: (StaffListItem?) -> Unit
) : RecyclerView.Adapter<StaffViewHolder>() {

    private var values: List<StaffListItem> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<StaffListItem>) {
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val binding = ActorPreviewBinding.inflate(LayoutInflater.from(parent.context))
        return StaffViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val item = values.getOrNull(position)

        with(holder.binding) {
            Glide.with(actorPhoto.context)
                .load(item?.posterUrl)
                .into(actorPhoto)
            actorName.text = item?.nameRu ?: item?.nameEn
            actorRole.text = item?.description ?: item?.professionText
            this.root.setOnClickListener {
                onClick(item)
            }

        }
    }

    override fun getItemCount(): Int = values.size
}

class StaffViewHolder(val binding: ActorPreviewBinding) : RecyclerView.ViewHolder(binding.root)