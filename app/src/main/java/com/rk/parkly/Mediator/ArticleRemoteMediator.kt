package com.rk.parkly.Mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rk.parkly.data.local.ArticleDatabase
import com.rk.parkly.data.model.Article
import com.rk.parkly.data.remote.NewsApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator (private val newsApi: NewsApi, private val articleDb: ArticleDatabase) : RemoteMediator<Int, Article>(){

private val articleDao = articleDb.getArticleDao()



    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        return try {
            val page = when(loadType){
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull() ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    (state.pages.sumOf { it.data.size } / state.config.pageSize) + 1
                }
            }

            val response = newsApi.getBreakingNews(countryCode = "us", pageNum = page)
            val articles = response.body()?.articles ?: emptyList()
            val endOfPaginationReached = articles.isEmpty()

            articleDb.withTransaction {
                    if (loadType == LoadType.REFRESH){
                        articleDao.deleteAllArticles()
                    }
                articleDao.insertArticles(articles)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e: IOException){
            MediatorResult.Error(e)
        }catch (e: HttpException){
            MediatorResult.Error(e)
        }

    }

}