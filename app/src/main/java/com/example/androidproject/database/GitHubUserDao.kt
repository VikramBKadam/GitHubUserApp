package com.example.androidproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidproject.model.GitHubUser


@Dao
interface GitHubUserDao {

    @Query("SELECT * FROM github_users")
    suspend fun getAllUsers(): List<GitHubUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<GitHubUser>)

    @Query("SELECT * FROM github_users WHERE login = :login")
    suspend fun getUserByLogin(login: String): GitHubUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(user: GitHubUser)

}
