package com.example.skillcinema.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.databinding.PageNumberBinding
import com.example.skillcinema.databinding.SeriesVhBinding
import com.example.skillcinema.entity.Episode
import com.example.skillcinema.entity.Season
import com.example.skillcinema.entity.SimilarFilmPreview

class SeriesAdapter : RecyclerView.Adapter<SeriesVH>() {

    private var values: List<Episode> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setEpisodeData(data: List<Episode>) {
        this.values = data
        notifyDataSetChanged()
    }

    fun setData(data: List<Season>) {
        this.values = data[0].episodes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesVH {
        val binding = SeriesVhBinding.inflate(LayoutInflater.from(parent.context))
        return SeriesVH(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SeriesVH, position: Int) {
        val item = values.getOrNull(position)

        with(holder.binding){
            seriesCountNameVH.text = "${item?.episodeNumber} серия. ${item?.nameRu ?: item?.nameEn}"
            textRelease.text = item?.releaseDate
        }
    }

    override fun getItemCount(): Int = values.size
}

class SeriesVH(val binding: SeriesVhBinding) : RecyclerView.ViewHolder(binding.root)