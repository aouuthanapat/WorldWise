package com.example.worldwise.presentation.screens.home

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.worldwise.R
import com.example.worldwise.presentation.screens.home.models.CategoryModel
import com.example.worldwise.presentation.screens.home.models.NewsModel
import com.example.worldwise.presentation.screens.profile.editPhoto.models.PhotoModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel(
    val application: Application
) : ViewModel() {

    private val _categoryModels: MutableStateFlow<List<CategoryModel>> =
        MutableStateFlow(emptyList())
    val categoryModels: Flow<List<CategoryModel>> get() = _categoryModels


    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: Flow<Boolean> get() = _isLoading


    private val _newsModels: MutableStateFlow<List<NewsModel>> = MutableStateFlow(emptyList())
    val newsModels: Flow<List<NewsModel>> get() = _newsModels

    private val newsList: ArrayList<NewsModel> = ArrayList()
    private val categoryList: ArrayList<CategoryModel> = ArrayList()

    init {
        getNews()
        buildCategoryItems()
        _categoryModels.value = categoryList
    }

    private fun getNews() {
        FirebaseDatabase.getInstance().getReference("News").get().addOnSuccessListener {
            for (dataSnapshot in it.children) {
                val news: NewsModel = dataSnapshot.getValue(NewsModel::class.java)!!
                newsList.add(news)
            }
        }.addOnCompleteListener {
            _newsModels.value = newsList
            _isLoading.value = false
        }
    }

    fun onCategoryClicked(category: CategoryModel) {
        _categoryModels.value = _categoryModels.value.map {
            if (it == category) {
                it.copy(isClicked = true)
            } else {
                it.copy(isClicked = false)
            }
        }
        if (category.title == application.getString(R.string.menu_main__common)) {
            _newsModels.value = newsList
        } else {
            _newsModels.value = newsList.filter { it.Category == category.title }
        }
    }


    private fun buildCategoryItems() {
        categoryList.addAll(
            listOf(
                CategoryModel(
                    title = application.getString(R.string.menu_main__common),
                    isClicked = true,
                ),
                CategoryModel(
                    title = application.getString(R.string.menu_main__food),
                    isClicked = false,
                ),
                CategoryModel(
                    title = application.getString(R.string.menu_main__transport),
                    isClicked = false,
                ),
                CategoryModel(
                    title = application.getString(R.string.menu_main__attractions),
                    isClicked = false,
                ),
                CategoryModel(
                    title = application.getString(R.string.menu_main__security),
                    isClicked = false,
                )
            )
        )
    }

    companion object {
        const val NEWS_ITEM_KEY = "news"
    }

}