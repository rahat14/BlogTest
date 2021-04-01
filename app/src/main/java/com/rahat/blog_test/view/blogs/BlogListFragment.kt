package com.rahat.blog_test.view.blogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahat.blog_test.R
import com.rahat.blog_test.data.Blog
import com.rahat.blog_test.databinding.FragmentBlogListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlogListFragment : Fragment(R.layout.fragment_blog_list), BlogAdapter.Interaction {
    private val viewModel: BlogListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // view binding
        val binding = FragmentBlogListBinding.bind(view)

        val taskAdapter = BlogAdapter(this@BlogListFragment)

        binding.apply {
            blogList.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        viewModel.blogsList.observe(viewLifecycleOwner) {

            if (it.isEmpty()) {
                // its empty
                viewModel.loadDemoDataList()
            } else {
                taskAdapter.submitList(it)
            }

        }



        binding.fabAddBlog.setOnClickListener {
            val bundle = Bundle()
            // passing data via bundle
            bundle.putSerializable("blog", null)
            bundle.putBoolean("is_edit", false)
            findNavController().navigate(R.id.action_blogListFragment_to_addEditFragment, bundle)
        }
        setHasOptionsMenu(true)


    }

    override fun onItemSelected(position: Int, item: Blog) {
        // click  event for the rcv row item
        val bundle = Bundle()
        // passing data via bundle
        bundle.putSerializable("blog", item)
        findNavController().navigate(R.id.action_blogListFragment_to_detailFragment, bundle)
    }
}