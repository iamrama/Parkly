package com.rk.parkly.di

import android.content.Context
import androidx.room.Room
import com.rk.parkly.data.local.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : ArticleDatabase {
        return Room.databaseBuilder(context,
            ArticleDatabase::class.java,
            "article_database.db").build()
    }
    @Provides
    @Singleton
    fun provideArticleDao(database: ArticleDatabase) = database.getArticleDao()

}
