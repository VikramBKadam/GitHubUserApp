package com.example.androidproject.ui

import android.content.Context
import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidproject.model.GitHubUser
import com.example.androidproject.model.Resource
import com.example.androidproject.view.GitHubUserDetailActivity
import com.example.androidproject.viewmodel.GitHubUserViewModel


@Composable
fun GitHubUserScreen(viewModel: GitHubUserViewModel = hiltViewModel(), context: Context) {
    val gitHubUsersState by viewModel.users.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (gitHubUsersState) {
            is Resource.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is Resource.Success -> {
                val users = (gitHubUsersState as Resource.Success<List<GitHubUser>>).data
                if (users.isNullOrEmpty()) EmptyStateScreen()
                else GitHubUserList(
                    users = users,
                    context,
                    onLoadMore = { viewModel.loadMoreUsers() })
            }
            is Resource.Error -> {
                (gitHubUsersState as Resource.Error).message?.let {
                    ErrorScreen(
                        message = it,
                        onRetry = { viewModel.fetchUsers() }
                    )
                }
            }
        }
    }
}

@Composable
fun GitHubUserList(users: List<GitHubUser>, context: Context, onLoadMore: () -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(users) { user ->
            GitHubUserItem(user = user, onClick = {
                val intent = Intent(context, GitHubUserDetailActivity::class.java)
                intent.putExtra("username", user.login)
                context.startActivity(intent)
            })
        }

        // Add a "Load More" button when the user reaches the bottom
        item {
            Button(
                onClick = onLoadMore,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Load More")
            }
        }
    }
}


@Composable
fun GitHubUserItem(user: GitHubUser, onClick: () -> Unit) {
    var isClicked by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isClicked) 1.05f else 1f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    user.login?.let { name ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    MaterialTheme.colors.surface,
                    shape = MaterialTheme.shapes.medium
                ) // Rounded corners
                .border(
                    1.dp,
                    MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                    shape = MaterialTheme.shapes.medium
                ) // Border with light shadow
                .fillMaxWidth()
                .clickable {
                    onClick()
                    isClicked = true // Trigger click animation
                }
                .graphicsLayer { scaleX = scale; scaleY = scale } // Apply scale transformation
                .animateContentSize() // Animate content size when state changes
                .padding(16.dp) // Padding around the content
        ) {
            Text(
                text = "User Name: $name",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Public Url: ${user.url ?: "No url available"}",
                style = MaterialTheme.typography.body2.copy(color = Color.Gray),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Type: ${user.type ?: "Unknown"}",
                    style = MaterialTheme.typography.body2
                )
                // we can add more info about user if needed in future
            }
        }
    }
}


@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colors.error)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun EmptyStateScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No users available.", style = MaterialTheme.typography.h6, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Please check back later.",
            style = MaterialTheme.typography.body2,
            color = Color.Gray
        )
    }
}
