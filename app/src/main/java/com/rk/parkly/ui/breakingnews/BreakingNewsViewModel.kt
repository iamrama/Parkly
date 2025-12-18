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
    val breakingNews: MutableLiveData<Result<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    var searchNews: MutableLiveData<Result<NewsResponse>> = MutableLiveData()

    val combinedNews: MutableLiveData<Result<List<Article>>> = MutableLiveData()

    val articles = newsRepository.getPaginatedArticles().cachedIn(viewModelScope)

    init {
        getBreakingNews("us")
    }

     fun getBreakingNews(countryCode: String ) = viewModelScope.launch {
        breakingNewsCall(countryCode)
    }

    fun breakingAndSearchNews(countryCode: String, searchQuery: String) = viewModelScope.launch {
            combinedNews.postValue(Result.Loading())

        if(!internetConnection(context)){
            combinedNews.postValue(Result.Error("No Internet Connection"))
            return@launch
        }

        try {
            val breakingNewsDeferred = async { newsRepository.getBreakingNews(countryCode, breakingNewsPage) }
            val searchNewsDeferred = async { newsRepository.getSearchNews(searchQuery, breakingNewsPage)  }

            val breakingNewsResult = breakingNewsDeferred.await()
            val searchResult = searchNewsDeferred.await()

            if (breakingNewsResult.isSuccessful && searchResult.isSuccessful){
                val combinedArticle = mutableListOf<Article>()

                breakingNewsResult.body()?.articles.let {
                    combinedArticle.addAll(it!!)
                }

                searchResult.body()?.articles.let {
                    combinedArticle.addAll(it!!)
                }

                combinedNews.postValue(Result.Success(combinedArticle))
            }else{
                combinedNews.postValue(Result.Error("Something went wrong"))
            }
        }catch (e: Exception){

        }
    }

    private suspend fun breakingNewsCall(countryCode: String) {
        breakingNews.postValue(Result.Loading())
        try {
           if (internetConnection(context)) {
                val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
                breakingNews.postValue(handleBreakingNewsResponse(response))
            }else{
               breakingNews.postValue(Result.Error("No Internet Connection"))
          }
        }catch (_: Exception){

        }
    }

    private suspend fun searchNewsCall(searchQuery: String){
        searchNews.postValue(Result.Loading())
        try {
            val resonse = newsRepository.getSearchNews(searchQuery, breakingNewsPage)
            searchNews.postValue(handleBreakingNewsResponse(resonse))
        }catch (_: Exception){

        }
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Result<NewsResponse> {
        if (response.isSuccessful){
            response.body().let { response ->
                breakingNewsPage++
                if (breakingNewsResponse == null){
                    breakingNewsResponse = response
                }else {
                    val oldArticle = breakingNewsResponse?.articles
                    val newArticle = response?.articles
                    oldArticle?.addAll(newArticle!!)
                }
                return Result.Success(breakingNewsResponse ?: response!!)
            }
        }else{
            return Result.Error(response.message())

        }
    }
}