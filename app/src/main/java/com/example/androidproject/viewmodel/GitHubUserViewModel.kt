package com.example.androidproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidproject.model.GitHubUser
import com.example.androidproject.model.Resource
import com.example.androidproject.repository.GitHubUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitHubUserViewModel @Inject constructor(
    private val repository: GitHubUserRepository,
) : ViewModel() {
    private var currentPage = 1
    private val perPage = 30

    // StateFlow for a list of users, initialized with loading state
    private val _users = MutableStateFlow<Resource<List<GitHubUser>>>(Resource.Loading())
    val users: StateFlow<Resource<List<GitHubUser>>> = _users

    // Fetch users when the ViewModel is initialized
    init {
        fetchUsers()
    }

    // Function to fetch users
    fun fetchUsers() {
        viewModelScope.launch {
            repository.getUsers(currentPage, perPage).collect { result ->
                _users.value = result // Update users state with the result
            }
        }
    }

    // Function to load more users by incrementing the page
    fun loadMoreUsers() {
        // Log.d("UserCheck", "page: $currentPage" + "perPage $perPage")
        currentPage++
        fetchUsers()  // Fetch users for the next page
    }
}
