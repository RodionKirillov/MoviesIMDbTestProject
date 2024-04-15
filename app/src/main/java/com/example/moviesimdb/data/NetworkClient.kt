package com.example.moviesimdb.data

import com.example.moviesimdb.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}