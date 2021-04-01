package com.rahat.blog_test.data.network

import com.rahat.blog_test.data.Author

data class BlogModel(
    val author: Author,
    val categories: List<String>,
    val cover_photo: String,
    val description: String,
    val id: Int,
    val title: String
)