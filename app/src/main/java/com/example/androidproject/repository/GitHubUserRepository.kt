package com.example.androidproject.repository


import com.example.androidproject.database.GitHubUserDao
import com.example.androidproject.model.GitHubUser
import com.example.androidproject.model.Resource
import com.example.androidproject.network.GitHubApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GitHubUserRepository @Inject constructor(
    private val apiService: GitHubApiService,
    private val gitHubUserDao: GitHubUserDao
) {

    suspend fun getUsers(page: Int, perPage: Int): Flow<Resource<List<GitHubUser>>> = flow {
        emit(Resource.Loading()) // Emit loading state initially

        try {
            // Fetch users from network based on pagination params
            val usersFromNetwork = apiService.getUsers(page, perPage)

            // Check if users exist in the database
            val usersFromDb = gitHubUserDao.getAllUsers()

            // If there are no users in DB, save the fetched network data
            if (usersFromDb.isEmpty()) {
                gitHubUserDao.insertAll(usersFromNetwork) // Save fetched users to the DB
            } else {
                // If users exist in DB, append new users to the existing list
                val combinedUsers = usersFromDb + usersFromNetwork
                gitHubUserDao.insertAll(usersFromNetwork) // Save new users to the DB
                emit(Resource.Success(combinedUsers)) // Emit combined data
            }

            // Emit success state with network data
            emit(Resource.Success(usersFromNetwork))

        } catch (exception: Exception) {
            emit(Resource.Error(exception.message ?: "Unknown Error")) // Handle any errors
        }
    }

    suspend fun getUserDetail(username: String): Flow<Resource<GitHubUser>> {
        return flow {
            emit(Resource.Loading()) // Emit loading state

            try {
                val user: GitHubUser? = gitHubUserDao.getUserByLogin(username)

                if (user?.name == null) {
                    val userDetail = apiService.getUserDetails(username) // Network call
                    gitHubUserDao.insertOrUpdate(userDetail)
                    emit(Resource.Success(userDetail))
                } else {
                    emit(Resource.Success(user))
                }

                // Emit success state
            } catch (e: Exception) {
                emit(Resource.Error("Failed to fetch user details: ${e.message}")) // Emit error state
            }
        }
    }
}
