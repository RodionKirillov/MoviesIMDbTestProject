package com.example.moviesimdb.data.network

import com.example.moviesimdb.data.dto.MovieCastResponse
import com.example.moviesimdb.data.dto.MovieDetailsResponse
import com.example.moviesimdb.data.dto.MoviesSearchResponse
import com.example.moviesimdb.data.dto.NamesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

//TODO https://tv-api.com/en/API/FullCast/k_zcuw1ytf/tt0110413
interface IMDbApiService {
    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>

    @GET("/en/API/Title/k_zcuw1ytf/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MovieDetailsResponse>

    @GET("/en/API/FullCast/k_zcuw1ytf/{movie_id}")
    fun getFullCast(@Path("movie_id") movieId: String): Call<MovieCastResponse>

    @GET("/en/API/SearchName/k_zcuw1ytf/{expression}")
    fun searchNames(@Path("expression") expression: String): Call<NamesSearchResponse>
}