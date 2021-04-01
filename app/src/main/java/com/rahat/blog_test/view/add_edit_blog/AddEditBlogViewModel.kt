package com.rahat.blog_test.view.add_edit_blog

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahat.blog_test.data.Blog
import com.rahat.blog_test.data.local.BlogDao
import com.rahat.blog_test.utils.HelperClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditBlogViewModel @Inject constructor(
    private val blogDao: BlogDao
) : ViewModel() {

    private val addEditBlogEventChannel = Channel<AddEditBlogEvent>()
    val addEditBlogEvent = addEditBlogEventChannel.receiveAsFlow()
    val categoryListString = MutableLiveData<String>()
    var selectedCategoryPosition = HelperClass.selectedList

    fun insertBlog(blog: Blog) = viewModelScope.launch {
        blogDao.insert(blog)
        addEditBlogEventChannel.send(AddEditBlogEvent.NavigateBackWithResult(0))
    }

    fun updateBlog(blog: Blog?) = viewModelScope.launch {
        if (blog != null) {
            blogDao.update(blog)
            addEditBlogEventChannel.send(AddEditBlogEvent.NavigateBackWithResult(0))
        } else {
            addEditBlogEventChannel.send(AddEditBlogEvent.ShowInvalidInputMessage("Please Check The Data"))
        }


    }

    fun setSelectCategoryPostionToFlase() {
        selectedCategoryPosition.forEachIndexed { index, b ->
            selectedCategoryPosition[index] = false
        }
    }

    fun calculateCategoryArray() {
        val categoryList = categoryListString.value?.split(",")
        HelperClass.CATEGORYLIST.forEachIndexed { i, value ->
            categoryList?.forEachIndexed { index, s ->
                if (s.trim() == value.trim()) {
                    selectedCategoryPosition[i] = true
                }

            }

        }

    }

    fun createDialogue(context: Context) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Choose Category")
        dialogBuilder.setCancelable(false)
        dialogBuilder.setMultiChoiceItems(
            HelperClass.CATEGORYLIST,
            selectedCategoryPosition
        ) { dialog, which, isChecked ->

            // Update the current focused item's checked status
            selectedCategoryPosition[which] = isChecked

        }
            .setPositiveButton("Ok")
            { dialog, which ->
            // as selectedCategoryPosition has the position of the selected category we can
            // loop it out and concat
            val categoryString: MutableList<String> = ArrayList()
            selectedCategoryPosition.forEachIndexed { index, b ->
                if (b) {
                    categoryString.add(HelperClass.CATEGORYLIST[index])
                }
            }
            categoryListString.value = categoryString.joinToString()
        }
        val dialog = dialogBuilder.create()
        // Display the alert dialog on interface
        dialog.show()

    }


    // for event happening  in the fragment
    sealed class AddEditBlogEvent {
        data class ShowInvalidInputMessage(val msg: String) : AddEditBlogEvent()
        data class NavigateBackWithResult(val result: Int) : AddEditBlogEvent()
    }
}