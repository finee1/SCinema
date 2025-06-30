package com.example.skillcinema.ui.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.skillcinema.R
import com.example.skillcinema.databinding.CollectionLayoutBinding
import com.example.skillcinema.ui.profile.FullListBDFragment

class CollectionsDBAdapter(
    private val deleteItem: (String) -> Unit
) : RecyclerView.Adapter<CollectionsDBViewHolder>() {

    private var values: LinkedHashMap<String, Int> = linkedMapOf()
    private var keys: List<String> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: LinkedHashMap<String, Int>, keys: List<String>) {
        this.values = data
        this.keys = keys
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsDBViewHolder {
        val binding = CollectionLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return CollectionsDBViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CollectionsDBViewHolder, position: Int) {
        val key = keys[position]
        val value = values[key]
        holder.binding.nameCollectionCard.text = key
        holder.binding.countCollectionCard.text = value.toString()

        holder.binding.btnDeleteCollection.setOnClickListener {
            deleteItem(key)
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(FullListBDFragment.TYPE_PARAM, FullListBDFragment.TYPE_COLLECTION)
                putString(FullListBDFragment.NAME_SELECTION_PARAM, key)
            }
            it.findNavController().navigate(
                R.id.action_navigation_notifications_btn_to_fullListBDFragment,
                bundle
            )
        }
    }

    override fun getItemCount(): Int = values.size
}

class CollectionsDBViewHolder(val binding: CollectionLayoutBinding) : RecyclerView.ViewHolder(binding.root)