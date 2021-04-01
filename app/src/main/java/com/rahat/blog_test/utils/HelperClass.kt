package com.rahat.blog_test.utils

import com.rahat.blog_test.data.Author

class HelperClass {
    companion object {
        const val BASE_URL: String =
            "https://my-json-server.typicode.com/sohel-cse/simple-blog-api/"
        val CATEGORYLIST = arrayOf<String>("Entertainment", "Lifestyle", "Productivity", "Business")
        val selectedList = booleanArrayOf(false, false, false, false)

        fun getAuthor(): Author {
            return Author("https://i.pravatar.cc/250", 1, "John Doe", "Content Writer")
        }
    }


}