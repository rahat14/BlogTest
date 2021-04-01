package com.rahat.blog_test.view.blog_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.rahat.blog_test.R
import com.rahat.blog_test.data.Blog
import com.rahat.blog_test.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.stateBlog(arguments?.getSerializable("blog") as Blog)
        }

        setUpView()

        binding.fabEditTask.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("blog", viewModel.blog.value)
            bundle.putBoolean("is_edit", true)
            findNavController().navigate(R.id.action_detailFragment_to_addEditFragment, bundle)
        }

        return binding.root

    }

    private fun setUpView() {

        viewModel.blog.observe(viewLifecycleOwner) {

            binding.coverImage.load(it.cover_photo)
            binding.authorTv.text = it.author.name
            binding.descTv.text = it.description
            binding.catgoryTv.text = it.category
            binding.titleTv.text = it.title

        }

    }


}