package com.rk.parkly.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rk.parkly.adapter.ArticleRecyclerviewAdapter.ArticleViewHoler
import com.rk.parkly.data.model.Article
import com.rk.parkly.databinding.ItemArticleLayoutBinding

class ArticleRecyclerviewAdapter :
    PagingDataAdapter<Article, ArticleViewHoler>(ArticleDiffCallback()){

    class ArticleViewHoler(private val binding: ItemArticleLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article){
            binding.apply {
                tvTitle.text = article.title
                tvAuthor.text = article.author ?: article.source?.name
                tvDate.text= article.formatPublishedAt

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHoler {
       val binding = ItemArticleLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHoler(binding)
    }

    override fun onBindViewHolder(
        holder: ArticleViewHoler,
        position: Int
    ) {
        val currentItem = getItem(position)
        if (currentItem != null){
            holder.bind(currentItem)
        }
    }

class ArticleDiffCallback: DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(
        oldItem: Article,
        newItem: Article
    ): Boolean {
        return oldItem.author == newItem.author
    }

    override fun areContentsTheSame(
        oldItem: Article,
        newItem: Article
    ): Boolean {
       return oldItem == newItem
    }

}


}