package com.example.moviesimdb.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.moviesimdb.data.NetworkClient
import com.example.moviesimdb.data.dto.MovieDetailsResponse
import com.example.moviesimdb.data.dto.MoviesIdRequest
import com.example.moviesimdb.data.dto.MoviesSearchRequest
import com.example.moviesimdb.data.dto.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitNetworkClient(
    private val imDbApiService: IMDbApiService,
    private val context: Context
) : NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }
        if (dto !is MoviesSearchRequest && dto !is MoviesIdRequest) {
            return Response().apply { resultCode = 400 }
        }

        val response = if (dto is MoviesSearchRequest) {
            imDbApiService.searchMovies(dto.expression).execute()
        } else {
            imDbApiService.getMovieDetails((dto as MoviesIdRequest).movieId).execute()
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