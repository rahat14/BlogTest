package com.rahat.blog_test.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "blog_list_table")
data class Blog(
    val cover_photo: String,
    var description: String,
    val id: Int,
    var title: String,
    var category: String, // will store the  category in json text via gson .
    // can overcome the situation with type-converters but it will brreak the database core mappings
    @Embedded val author: Author,
    @PrimaryKey(autoGenerate = true) val p_id: Int = 0
): Serializable







