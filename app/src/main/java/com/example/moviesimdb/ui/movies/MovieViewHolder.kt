package com.example.moviesimdb.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesimdb.R
import com.example.moviesimdb.domain.models.Movie

class MovieViewHolder(
    parent: ViewGroup,
    private val onMovieClickListener: MoviesAdapter.MovieClickListener
) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movie, parent, false)
    ) {

    private var cover: ImageView = itemView.findViewById(R.id.cover)
    private var title: TextView = itemView.findViewById(R.id.title)
    private var description: TextView = itemView.findViewById(R.id.description)
    private var inFavoriteToggle: Button = itemView.findViewById(R.id.favorite)

    fun bind(movie: Movie) {
        Glide.with(itemView)
            .load(movie.image)
            .into(cover)

        title.text = movie.title
        description.text = movie.description

        inFavoriteToggle.text = getFavoriteToggleDrawable(movie.isFavorite)

        itemView.setOnClickListener { onMovieClickListener.onMovieClick(movie) }

        inFavoriteToggle.setOnClickListener { onMovieClickListener.onFavoriteToggleClick(movie) }
    }

    private fun getFavoriteToggleDrawable(isFavorite: Boolean): String {
        return if (isFavorite) {
            "FAVORITE"
        } else {
            "NOT FAVORITE"
        }
    }
}