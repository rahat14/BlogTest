package com.rahat.blog_test.network

import com.rahat.blog_test.data.network.BlogResponse
import retrofit2.http.GET

interface ApiService {
    @GET("db")
    suspend fun getDemoBlogList(
    ): BlogResponse
}