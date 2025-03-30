package com.example.androidproject.view

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidproject.model.GitHubUser
import com.example.androidproject.ui.GitHubUserDetailScreen
import com.example.androidproject.ui.GitHubUserScreen
import com.example.androidproject.ui.TopAppBar

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GitHubUserActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colors = lightColors(
                    primary = Color.Blue,
                    onPrimary = Color.White,
                    background = Color.White,
                    onBackground = Color.Black
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        TopAppBar()
                        GitHubUserApp(LocalContext.current)
                    }
                }
            }
        }
    }
}

@Composable
fun GitHubUserApp(context: Context) {
    MaterialTheme(
        colors = lightColors(
            primary = Color.Blue,
            onPrimary = Color.White,
            background = Color.White,
            onBackground = Color.Black
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            GitHubUserScreen(context = context)
        }
    }
}
