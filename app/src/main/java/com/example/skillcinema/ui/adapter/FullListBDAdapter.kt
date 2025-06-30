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
import com.example.skillcinema.databinding.FilmPreviewBinding
import com.example.skillcinema.entity.BaseModelDBTable

class FullListBDAdapter(
    private val checkWatched: (Int) -> Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var values: List<BaseModelDBTable> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<BaseModelDBTable>) {
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FilmPreviewBinding.inflate(LayoutInflater.from(parent.context))
        return MyNewViewHolder(binding)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (values.isNotEmpty()) {
            val item = values.getOrNull(position)
            val binding = (holder as MyNewViewHolder).binding
            with(binding) {
                if (item?.ratingKinopoisk == null) {
                    rating.visibility = View.INVISIBLE
                } else {
                    rating.text = item.ratingKinopoisk.toString()
                }
                nameFilm.text = item?.nameRu ?: item?.nameOriginal
                genre.text = item?.genres ?: ""

                if (checkWatched(item?.kinopoiskId!!)){
                    isWatched.visibility = View.VISIBLE
                }

                item.let {
                    Glide.with(poster.context)
                        .load(it?.posterUrl)
                        .centerCrop()
                        .into(poster)
                }

                this.root.setOnClickListener {
                    val bundle = Bundle().apply {
                        putInt("param1", item?.kinopoiskId ?: 1)
                    }
                    it.findNavController()
                        .navigate(R.id.action_fullListBDFragment_to_filmFragment, bundle)
                }
            }
        }
    }

    override fun getItemCount(): Int = values.size
}