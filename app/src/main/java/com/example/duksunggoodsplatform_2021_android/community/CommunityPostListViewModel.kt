package com.example.duksunggoodsplatform_2021_android.community

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CommunityPostListViewModel(val dataSource: CommunityDataSource) : ViewModel() {
    val communityPostsLiveData = dataSource.getCommunityPostList()
}

class CommunityPostViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommunityPostListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommunityPostListViewModel(
                dataSource = CommunityDataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

