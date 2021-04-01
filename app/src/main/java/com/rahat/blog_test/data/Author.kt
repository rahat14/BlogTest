package com.rahat.blog_test.data

import androidx.room.ColumnInfo
import java.io.Serializable

data class Author(
    val avatar: String,
    @ColumnInfo(name = "author_id") val id: Int,
    val name: String,
    val profession: String
): Serializable