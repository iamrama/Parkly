package com.rk.parkly.ui.breakingnews

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rk.parkly.data.model.Article

import com.rk.parkly.data.model.NewsResponse
import com.rk.parkly.repository.NewsRepository
import com.rk.parkly.util.NetworkUtil.Companion.internetConnection
import com.rk.parkly.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(private val newsRepository: NewsRepository, @ApplicationContext private val context: Context) : ViewModel(){


    val articles = newsRepository.getPaginatedArticles().cachedIn(viewModelScope)

}
