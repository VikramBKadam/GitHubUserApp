package com.example.androidproject.di

import android.content.Context
import androidx.room.Room
import com.example.androidproject.database.GitHubUserDao
import com.example.androidproject.database.GitHubUserDatabase
import com.example.androidproject.network.GitHubApiService
import com.example.androidproject.network.GitHubApiService.Companion.BASE_URL
import com.example.androidproject.repository.GitHubUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GitHubUserAppModule {

    @Provides
    fun provideGitHubApiService(): GitHubApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApiService::class.java)
    }

    @Provides
    fun provideGitHubUserDatabase(@ApplicationContext context: Context): GitHubUserDatabase {
        return Room.databaseBuilder(context, GitHubUserDatabase::class.java, "github_user_db")
            .build()
    }

    @Provides
    fun provideGitHubUserDao(database: GitHubUserDatabase): GitHubUserDao {
        return database.gitHubUserDao()
    }

    @Singleton
    @Provides
    fun provideGitHubUserRepository(
        apiService: GitHubApiService,
        gitHubUserDao: GitHubUserDao
    ): GitHubUserRepository {
        return GitHubUserRepository(apiService, gitHubUserDao)
    }
}
