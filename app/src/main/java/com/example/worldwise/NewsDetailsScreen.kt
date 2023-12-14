package com.example.worldwise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.databinding.FragmentNewsDetailsScreenBinding
import com.example.worldwise.presentation.screens.home.HomeViewModel
import com.example.worldwise.presentation.screens.home.models.NewsModel


class NewsDetailsScreen : Fragment(R.layout.fragment_news_details_screen) {

    private val binding by viewBinding(FragmentNewsDetailsScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val news:NewsModel = arguments?.getSerializable(HomeViewModel.NEWS_ITEM_KEY) as NewsModel
        binding.theme.text = news.Theme
        binding.title.text = news.Title
        binding.description.text = news.Text

    }

}