package com.rahat.blog_test.view.blogs.repository


import com.rahat.blog_test.data.network.BlogResponse
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dataRepository: NetworkRepository
) {
    suspend fun getDemoBlogsList(): BlogResponse {
        return dataRepository.getRepositoriesList()
    }
}
