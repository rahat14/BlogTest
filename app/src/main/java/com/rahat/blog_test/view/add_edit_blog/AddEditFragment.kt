package com.rahat.blog_test.view.add_edit_blog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rahat.blog_test.data.Blog
import com.rahat.blog_test.databinding.FragmentAddEditBlogBinding
import com.rahat.blog_test.utils.HelperClass
import com.rahat.blog_test.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@Suppress("IMPLICIT_CAST_TO_ANY")
@AndroidEntryPoint
class AddEditFragment : Fragment() {
    private val viewModel: AddEditBlogViewModel by viewModels()
    var blog: Blog? = null
    var coverPhoto: String = "Test Cover"
    private var ID: Int = 0
    var isEdit: Boolean = false
    private lateinit var binding: FragmentAddEditBlogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // view binding
        binding = FragmentAddEditBlogBinding.inflate(layoutInflater, container, false)


        setupView()

        binding.fabSaveTask.setOnClickListener {

            if (isEdit) {
                viewModel.updateBlog(createUpdateBlog())
            } else {
                viewModel.insertBlog(createNewBlog())
            }
        }

        binding.category.setOnClickListener {
            viewModel.calculateCategoryArray()
            viewModel.createDialogue(requireContext())
        }

        // observer
        viewModel.categoryListString.observe(viewLifecycleOwner) {

            binding.category.text = it.toString()

        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.setSelectCategoryPostionToFlase()
            viewModel.addEditBlogEvent.collect { event ->
                when (event) {

                    is AddEditBlogViewModel.AddEditBlogEvent.ShowInvalidInputMessage -> {
                        Toast.makeText(context, "Msg -> ${event.msg}", Toast.LENGTH_LONG).show()
                    }
                    is AddEditBlogViewModel.AddEditBlogEvent.NavigateBackWithResult -> {

                        findNavController().popBackStack()

                    }

                }.exhaustive
            }
        }

        return binding.root
    }

    private fun setupView() {

        if (arguments?.getBoolean("is_edit") == true) {
            isEdit = true
            // recieved data via Bundle
            blog = arguments?.getSerializable("blog") as Blog
            binding.editTextBlogName.setText(blog!!.title)
            binding.editDescriptionBlogName.setText(blog!!.description)
            binding.category.text = blog!!.category
            viewModel.categoryListString.value = blog!!.category
            coverPhoto = blog!!.cover_photo
            ID = blog!!.id
        } else {
            isEdit = false
        }

    }


    private fun createUpdateBlog(): Blog? {

        blog?.title = binding.editTextBlogName.text.toString()
        blog?.description = binding.editDescriptionBlogName.text.toString()
        blog?.category = viewModel.categoryListString.value.toString()

        return blog
    }

    private fun createNewBlog(): Blog {


        val BLOG = Blog(
            coverPhoto,
            binding.editDescriptionBlogName.text.toString(),
            ID,
            binding.editTextBlogName.text.toString(),
            viewModel.categoryListString.toString(),
            HelperClass.getAuthor()
        )


        return BLOG
    }


    override fun onResume() {
        super.onResume()
        viewModel.setSelectCategoryPostionToFlase()
    }


}
