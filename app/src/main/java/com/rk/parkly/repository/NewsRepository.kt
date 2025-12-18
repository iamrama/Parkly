package com.rk.parkly.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rk.parkly.Mediator.ArticleRemoteMediator
import com.rk.parkly.data.local.ArticleDatabase
import com.rk.parkly.data.model.Article
import com.rk.parkly.data.model.NewsResponse
import com.rk.parkly.data.remote.NewsApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi, private val articleDb: ArticleDatabase) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPaginatedArticles(): Flow<PagingData<Article>>{
        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                remoteMediator = ArticleRemoteMediator(newsApi = newsApi, articleDb = articleDb),
                pagingSourceFactory = {articleDb.getArticleDao().getAllArticles()}

            ).flow
    }

    suspend fun getBreakingNews(countryCode: String, pagenum: Int): Response<NewsResponse>{

        return newsApi.getBreakingNews(countryCode, pagenum)

    }

    suspend fun getSearchNews(searchQuery: String, pageNum:Int): Response<NewsResponse>{
        return newsApi.getSearchNews(searchQuery,pageNum)
    }
}