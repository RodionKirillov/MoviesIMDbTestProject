package com.example.moviesimdb.ui.name

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesimdb.R
import com.example.moviesimdb.databinding.ListItemNameBinding
import com.example.moviesimdb.domain.models.Result

class NameViewHolder(
    private val binding: ListItemNameBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Result) {
        binding.title.text = model.title
        binding.description.text = model.description
        Glide.with(itemView.context)
            .load(model.image)
            .placeholder(R.drawable.ic_launcher_background)
            .circleCrop()
            .into(binding.cover)
    }
}