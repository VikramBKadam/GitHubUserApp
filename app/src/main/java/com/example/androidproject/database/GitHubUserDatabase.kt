package com.example.androidproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidproject.model.GitHubUser


@Database(entities = [GitHubUser::class], version = 1)
abstract class GitHubUserDatabase : RoomDatabase() {
    abstract fun gitHubUserDao(): GitHubUserDao
}
