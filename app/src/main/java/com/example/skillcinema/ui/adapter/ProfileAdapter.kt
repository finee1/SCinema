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
import com.example.skillcinema.databinding.ClearDbLayoutBinding
import com.example.skillcinema.databinding.FilmPreviewBinding
import com.example.skillcinema.databinding.RightArrowBinding
import com.example.skillcinema.entity.BaseModelDBTable

class ProfileAdapter(
    private val onClick: () -> Unit,
    private val checkWatched: (Int) -> Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        const val SHOW_ALL_BTN_COUNT = 1
    }

    private var ITEM_COUNT = 0

    private var values: List<BaseModelDBTable> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<BaseModelDBTable>) {
        this.values = data
        ITEM_COUNT = values.size
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.film_preview -> {
                val binding = FilmPreviewBinding.inflate(LayoutInflater.from(parent.context))
                MyNewViewHolder(binding)
            }
            R.layout.clear_db_layout -> {
                val binding = ClearDbLayoutBinding.inflate(LayoutInflater.from(parent.context))
                ClearDBViewHolder(binding)
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (values.isNotEmpty()) {
            when (getItemViewType(position)) {
                R.layout.film_preview -> {
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
                                .navigate(R.id.action_navigation_notifications_btn_to_filmFragment, bundle)
                        }
                    }
                }
                R.layout.clear_db_layout -> {
                    holder.itemView.setOnClickListener {
                        onClick()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = if (values.isEmpty()) 0 else values.size + SHOW_ALL_BTN_COUNT

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            ITEM_COUNT -> R.layout.clear_db_layout
            else -> R.layout.film_preview
        }
    }
}

class ClearDBViewHolder(val binding: ClearDbLayoutBinding) : RecyclerView.ViewHolder(binding.root)