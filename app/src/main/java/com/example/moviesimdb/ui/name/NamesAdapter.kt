package com.example.moviesimdb.ui.name

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesimdb.databinding.ListItemNameBinding
import com.example.moviesimdb.domain.models.Result

class NamesAdapter : RecyclerView.Adapter<NameViewHolder>() {

     var names = ArrayList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val binding = ListItemNameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        holder.bind(names[position])
    }

    override fun getItemCount(): Int = names.size
}