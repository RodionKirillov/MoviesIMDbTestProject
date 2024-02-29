package com.example.moviesimdb.domain.models

data class Movie(
    val id: String,
    val resultType: String,
    val image: String,
    val title: String,
    val description: String,
    val isFavorite: Boolean
)