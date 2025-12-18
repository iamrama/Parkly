package com.rk.parkly.data.model

data class NewsResponse(val articles: MutableList<Article>, val status: String, val totalResults: Int)