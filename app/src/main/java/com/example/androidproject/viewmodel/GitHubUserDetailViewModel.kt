package com.example.androidproject.viewmodel

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
class GitHubUserDetailViewModel @Inject constructor(
    private val repository: GitHubUserRepository
) : ViewModel() {

    // StateFlow to observe the user detail state
    private val _userDetail = MutableStateFlow<Resource<GitHubUser>>(Resource.Loading())
    val userDetail: StateFlow<Resource<GitHubUser>> = _userDetail

    // Fetch user detail and collect the flow to update state
    fun fetchUserDetail(username: String) {
        viewModelScope.launch {
            repository.getUserDetail(username).collect { result ->
                _userDetail.value = result // Collect and update state
            }
        }
    }
}
