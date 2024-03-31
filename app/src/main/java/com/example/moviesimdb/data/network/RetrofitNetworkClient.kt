package com.example.moviesimdb.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.moviesimdb.data.NetworkClient
import com.example.moviesimdb.data.dto.MovieCastRequest
import com.example.moviesimdb.data.dto.MoviesIdRequest
import com.example.moviesimdb.data.dto.MoviesSearchRequest
import com.example.moviesimdb.data.dto.NamesSearchRequest
import com.example.moviesimdb.data.dto.Response


class RetrofitNetworkClient(
    private val imdbService: IMDbApiService,
    private val context: Context,
) : NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }

        if ((dto !is MoviesSearchRequest)
            && (dto !is MoviesIdRequest)
            && (dto !is MovieCastRequest)
            && (dto !is NamesSearchRequest)) {
            return Response().apply { resultCode = 400 }
        }

        val response = when (dto) {
            is NamesSearchRequest -> imdbService.searchNames(dto.expression).execute()
            is MoviesSearchRequest -> imdbService.searchMovies(dto.expression).execute()
            is MoviesIdRequest -> imdbService.getMovieDetails(dto.movieId).execute()
            else -> imdbService.getFullCast((dto as MovieCastRequest).movieId).execute()
        }
        val body = response.body()
        return if (body != null) {
            body.apply { resultCode = response.code() }
        } else {
            Response().apply { resultCode = response.code() }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}