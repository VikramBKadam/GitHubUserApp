package com.example.androidproject

import com.example.androidproject.model.GitHubUser
import com.example.androidproject.model.Resource
import com.example.androidproject.repository.GitHubUserRepository
import com.example.androidproject.viewmodel.GitHubUserViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


class GitHubUserViewModelTest {



    private lateinit var viewModel: GitHubUserViewModel
    private var mockRepository: GitHubUserRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        // Injects Hilt dependencies
        viewModel = GitHubUserViewModel(mockRepository) // Initialize the ViewModel
    }

    @Test
    fun `test fetch users should update state to success`() = runBlockingTest {
        // Prepare mock data for users
        val mockUsers = listOf(
            dummyUser1,
            dummyUser2
        )

        // Stub the repository to return a success response
        val mockResource = Resource.Success(mockUsers)
        coEvery { mockRepository.getUsers(1, 30) } returns flowOf(mockResource)

        // Trigger the fetchUsers() method
        viewModel.fetchUsers()
        testDispatcher.scheduler.runCurrent()
        val value = viewModel.users.value
        assertTrue(value is Resource.Success)
        assertEquals(mockUsers, (value as Resource.Success).data)
    }

    @Test
    fun `test fetch users should update state to error`() = runBlockingTest {
        // Stub the repository to return an error response
        val mockResource = Resource.Error<List<GitHubUser>>("Error fetching users")
        coEvery { mockRepository.getUsers(1, 30) } returns flowOf(mockResource)

        // Trigger the fetchUsers() method
        viewModel.fetchUsers()
        testDispatcher.scheduler.runCurrent()
        // Observe the users' state and check if it's updated to Error
        val value = viewModel.users.value
        assertTrue(value is Resource.Error)
        assertEquals("Error fetching users", (value as Resource.Error).message)
    }

    @Test
    fun `test fetch users should update state to loading initially`() = runBlockingTest {
        // Stub the repository to return a loading state
        val mockResource = Resource.Loading<List<GitHubUser>>()

        coEvery { mockRepository.getUsers(1, 30) } returns flowOf(mockResource)

        // Trigger the fetchUsers() method
        viewModel.fetchUsers()
        testDispatcher.scheduler.runCurrent()
        // Observe the users' state and check if it's initially in Loading state
        val value = viewModel.users.value
        assertTrue(value is Resource.Loading)
    }
}

@After
fun tearDown() {
    Dispatchers.resetMain()
}

val dummyUser1 = GitHubUser(
    id = 1,
    login = "octocat",
    node_id = "MDQ6VXNlcjE=",
    avatar_url = "https://avatars.githubusercontent.com/u/1?v=4",
    gravatar_id = "00000000000000000000000000000000",
    url = "https://api.github.com/users/octocat",
    html_url = "https://github.com/octocat",
    followers_url = "https://api.github.com/users/octocat/followers",
    following_url = "https://api.github.com/users/octocat/following{/other_user}",
    gists_url = "https://api.github.com/users/octocat/gists{/gist_id}",
    starred_url = "https://api.github.com/users/octocat/starred{/owner}{/repo}",
    subscriptions_url = "https://api.github.com/users/octocat/subscriptions",
    organizations_url = "https://api.github.com/users/octocat/orgs",
    repos_url = "https://api.github.com/users/octocat/repos",
    events_url = "https://api.github.com/users/octocat/events{/privacy}",
    received_events_url = "https://api.github.com/users/octocat/received_events",
    type = "User",
    site_admin = false,
    name = "The Octocat",
    company = "@GitHub",
    blog = "https://github.blog",
    location = "San Francisco",
    email = "octocat@github.com",
    hireable = false,
    bio = "A friendly robot that helps with GitHub.",
    twitter_username = "github",
    public_repos = 100,
    public_gists = 30,
    followers = 2000,
    following = 50,
    created_at = "2011-01-25T18:44:36Z",
    updated_at = "2021-10-15T14:53:22Z"
)

val dummyUser2 = GitHubUser(
    id = 2,
    login = "johnsmith",
    node_id = "MDQ6VXNlcjI=",
    avatar_url = "https://avatars.githubusercontent.com/u/2?v=4",
    gravatar_id = "00000000000000000000000000000000",
    url = "https://api.github.com/users/johnsmith",
    html_url = "https://github.com/johnsmith",
    followers_url = "https://api.github.com/users/johnsmith/followers",
    following_url = "https://api.github.com/users/johnsmith/following{/other_user}",
    gists_url = "https://api.github.com/users/johnsmith/gists{/gist_id}",
    starred_url = "https://api.github.com/users/johnsmith/starred{/owner}{/repo}",
    subscriptions_url = "https://api.github.com/users/johnsmith/subscriptions",
    organizations_url = "https://api.github.com/users/johnsmith/orgs",
    repos_url = "https://api.github.com/users/johnsmith/repos",
    events_url = "https://api.github.com/users/johnsmith/events{/privacy}",
    received_events_url = "https://api.github.com/users/johnsmith/received_events",
    type = "User",
    site_admin = false,
    name = "John Smith",
    company = "Acme Corp",
    blog = "https://johnsmith.dev",
    location = "New York",
    email = "john.smith@example.com",
    hireable = true,
    bio = "Developer and tech enthusiast.",
    twitter_username = "johnsmith_dev",
    public_repos = 45,
    public_gists = 15,
    followers = 1500,
    following = 75,
    created_at = "2015-06-17T09:20:52Z",
    updated_at = "2021-12-10T10:25:33Z"
)
