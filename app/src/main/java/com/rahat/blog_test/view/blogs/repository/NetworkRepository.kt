package com.rahat.blog_test.view.blogs.repository

import com.rahat.blog_test.data.network.BlogResponse
import com.rahat.blog_test.network.ApiService
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getRepositoriesList(): BlogResponse {
        return apiService.getDemoBlogList()
    }
}