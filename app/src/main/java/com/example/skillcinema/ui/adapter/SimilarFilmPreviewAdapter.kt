package com.example.skillcinema.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.databinding.FilmPreviewBinding
import com.example.skillcinema.entity.SimilarFilmPreview

class SimilarFilmPreviewAdapter(
    private val onClick: (SimilarFilmPreview) -> Unit
) :
    RecyclerView.Adapter<MyNewViewHolder>() {

    companion object {
        const val ITEM_COUNT = 20
    }

    private var values: List<SimilarFilmPreview> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<SimilarFilmPreview>) {
        this.values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNewViewHolder {
        val binding = FilmPreviewBinding.inflate(LayoutInflater.from(parent.context))
        return MyNewViewHolder(binding)

    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyNewViewHolder, position: Int) {
        val item = values.getOrNull(position)

        with(holder.binding) {
            rating.visibility = View.GONE
            Glide.with(poster.context)
                .load(item?.posterUrlPreview)
                .into(poster)

            nameFilm.text = item?.nameRu ?: item?.nameOriginal

            this.root.setOnClickListener {
                item?.let { film -> onClick(film) }
            }
        }
    }


    override fun getItemCount(): Int = if (values.size > 20) ITEM_COUNT else values.size
}