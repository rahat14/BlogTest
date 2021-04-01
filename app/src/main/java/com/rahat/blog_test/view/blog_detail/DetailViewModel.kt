package com.rahat.blog_test.view.blog_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahat.blog_test.data.Blog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : ViewModel() {


    var blog = MutableLiveData<Blog>()

    fun stateBlog(rcvBlog: Blog) {

        blog.value = rcvBlog

    }

}