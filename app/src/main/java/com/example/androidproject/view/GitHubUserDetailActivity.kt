package com.example.androidproject.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidproject.ui.GitHubUserDetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GitHubUserDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val username = intent.getStringExtra("username") ?: ""
            GitHubUserDetailScreen(username = username)
        }
    }
}
