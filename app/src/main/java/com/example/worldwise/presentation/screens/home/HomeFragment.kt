package com.example.worldwise.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.R
import com.example.worldwise.databinding.FragmentHomeBinding
import com.example.worldwise.presentation.screens.home.HomeViewModel.Companion.NEWS_ITEM_KEY
import com.example.worldwise.presentation.screens.home.adapter.category.CategoryAdapter
import com.example.worldwise.presentation.screens.home.adapter.news.NewsAdapter
import com.example.worldwise.utils.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModel()

    private var categoryAdapter: CategoryAdapter? = null
    private var newsAdapter: NewsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryAdapter = CategoryAdapter {
            viewModel.onCategoryClicked(it)
        }

        newsAdapter = NewsAdapter {
            val bundle = Bundle()
            bundle.putSerializable(NEWS_ITEM_KEY, it)
            findNavController().navigate(R.id.action_home_to_news_details_screen, bundle)
        }
        binding.recyclerViewCategories.adapter = categoryAdapter
        binding.recyclerViewNews.adapter = newsAdapter
        bindViewModelOutputs()
    }

    private fun bindViewModelOutputs() = with(viewModel) {
        categoryModels.bind(viewLifecycleOwner) {
            categoryAdapter?.submitList(it)
        }

        newsModels.bind(viewLifecycleOwner) {
            newsAdapter?.submitList(it)
        }

        isLoading.bind(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
            binding.recyclerViewNews.isVisible = !it
            binding.recyclerViewCategories.isVisible = !it
        }
    }
}
