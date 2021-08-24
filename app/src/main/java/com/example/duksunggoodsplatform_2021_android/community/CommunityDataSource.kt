package com.example.duksunggoodsplatform_2021_android.community

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CommunityDataSource(resources: Resources) {
    private val initialPostList = communityPostList(resources)
    private val communityPostLiveData = MutableLiveData(initialPostList)

    fun getPostForId(id: Long): CommunityPost? {
        communityPostLiveData.value?.let {
            return it.firstOrNull{ it.id == id }
        }
        return null
    }

    fun getCommunityPostList(): LiveData<List<CommunityPost>> {
        return communityPostLiveData
    }

    companion object {
        private var INSTANCE: CommunityDataSource? = null

        fun getDataSource(resources: Resources): CommunityDataSource {
            return synchronized(CommunityDataSource::class) {
                val newInstance = INSTANCE ?: CommunityDataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}