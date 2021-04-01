package com.rahat.blog_test.view.blogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rahat.blog_test.data.Blog
import com.rahat.blog_test.data.local.BlogDao
import com.rahat.blog_test.view.blogs.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BlogListViewModel @Inject constructor(

    private val blogDao: BlogDao,
    private val networkRepository: RepositoryImpl

) : ViewModel() {

    /*
     as  we need to load the data from the internet for 1st time only we can use multiple way
     -> check if the app launch only 1st time via data store or shared pref
     or
     -> we can check if the blog database is intial empty or not if empty then we can get the data fro,
     the internet  i will proceed with this method

     */

    // check if the  initialBlogList is empty or not
    val blogsList = blogDao.getBlogList().asLiveData()


     fun loadDemoDataList() {

        viewModelScope.launch {
            withContext(Dispatchers.Default) {

                val demoBlogListResponse = networkRepository.getDemoBlogsList()

                // we receive the response the sever and now we insert in a Default thread / cpu intensive work only
                for (item in demoBlogListResponse.blogs) {
                    // converting the category list to string before
                    val blog = Blog(
                        item.cover_photo, item.description,
                        item.id, item.title, item.categories.joinToString(), item.author
                    )
                    blogDao.insert(blog)
                }
            }
        }

    }



}