# GitHubUserApp

A simple Android app to fetch and display GitHub user details using the GitHub API. This app allows users to view basic information about GitHub users, including their profile, repositories, followers, and more.

## Features

- Display GitHub user details
- Fetch user information from the GitHub API
- Show profile, followers, repositories, etc.
- Clean and responsive UI

## Tech Stack

- **Kotlin**: The primary language used for the Android app development.
- **Jetpack Compose**: UI toolkit for building modern, declarative UIs.
- **Retrofit**: HTTP client for making network calls to the GitHub API.
- **Room Database**: Used for local caching of user data.
- **Dagger Hilt**: Dependency injection library for managing app dependencies.
- **GitHub API**: The RESTful API to fetch user details.

## Architecture

This app follows the **MVVM (Model-View-ViewModel)** pattern for separation of concerns:

- **Model**: Represents the data layer. This includes the data classes, repository, and network services.
- **View**: The UI layer built using Jetpack Compose.
- **ViewModel**: Acts as a bridge between the View and Model. It holds the app’s UI-related data in a lifecycle-conscious way.

## Flow

1. **Launch The App**: Launch the app to fetch the users.
2. **API Call**: The app makes a network request to the GitHub API using Retrofit to fetch users.
3. **Data Parsing**: The response from the API is parsed and mapped to a `GitHubUser` data class.
4. **Display**: The data is displayed in the UI using Jetpack Compose.
5. **Caching**: The fetched data is saved locally using Room, allowing offline access to user data.
6. **Click on User** Click on user to fetch the user details.

**Demo of App:<br/>**


https://github.com/user-attachments/assets/35f32971-6d49-4db9-8bbe-79459af9b5e7




## Setup Instructions

### Clone the repository

```bash
git clone https://github.com/VikramBKadam/GitHubUserApp.git


