package com.example.androidproject.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.androidproject.model.GitHubUser
import com.example.androidproject.model.Resource
import com.example.androidproject.util.Utils
import com.example.androidproject.viewmodel.GitHubUserDetailViewModel

@Composable
fun GitHubUserDetailScreen(
    username: String,
    viewModel: GitHubUserDetailViewModel = hiltViewModel()
) {
    val userDetailState by viewModel.userDetail.collectAsState()

    // Fetch user details when the username changes or the screen is recomposed
    LaunchedEffect(username) {
        viewModel.fetchUserDetail(username)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (userDetailState) {
            is Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is Resource.Success -> {
                val user = (userDetailState as Resource.Success<GitHubUser>).data
                user?.let { GitHubUserDetail(user = it) }
            }
            is Resource.Error -> {
                val message =
                    (userDetailState as Resource.Error).message ?: "Failed to load user details."
                ErrorScreen(message = message, onRetry = { viewModel.fetchUserDetail(username) })
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GitHubUserDetail(user: GitHubUser) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar Image
        GlideImage(
            model = user.avatar_url,
            contentDescription = "User Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Name
        Text(
            text = user.name ?: "N/A",
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.primary
        )

        // Twitter Username
        user.twitter_username?.let {
            Text(
                text = "Twitter: @$it",
                fontSize = 14.sp,
                color = androidx.compose.ui.graphics.Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Bio
        Text(
            text = user.bio ?: "No bio available",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Additional User Details
        InfoRow(label = "Location", value = user.location)
        InfoRow(label = "Company", value = user.company)
        InfoRow(label = "Blog", value = user.blog)
        InfoRow(label = "Followers", value = user.followers?.toString())
        InfoRow(label = "Following", value = user.following?.toString())
        InfoRow(label = "Public Repos", value = user.public_repos?.toString())
        InfoRow(label = "Created At", value = user.created_at?.let { Utils.formatDateTime(it) })
        InfoRow(label = "Updated At", value = user.updated_at?.let { Utils.formatDateTime(it) })
    }
}

@Composable
fun InfoRow(label: String, value: String?) {
    if (!value.isNullOrEmpty()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "$label:", fontWeight = FontWeight.Bold)
            Text(text = value)
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}

