package com.example.nytimes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.nytimes.dao.NYTSearchResponse
import com.example.nytimes.dao.NewsResponse
import com.example.nytimes.dao.SearchArticle
import com.example.nytimes.service.NewsApiService
import com.example.nytimes.viewmodel.ListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ExperimentalCoroutinesApi
class ListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var viewModel: ListViewModel
    private val apiService: NewsApiService = mock(NewsApiService::class.java)
    private val mockPopularResponse: NewsResponse = mock(NewsResponse::class.java)
    private val mockSearchResponse: NYTSearchResponse = mock(NYTSearchResponse::class.java)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ListViewModel()
        viewModel.apiService = apiService
    }

    @Test
    fun `test popularArticles success`(): Unit = testScope.runBlockingTest {
        val section = "viewed"
        val apiKey = "Gc5uGXPTZ3noAA5ntzAjL7ZOfqBZCYP"

        // Setup mock response
        val call: Call<NewsResponse> = mock(Call::class.java) as Call<NewsResponse>
        `when`(apiService.getMostPopularNews(section, apiKey)).thenReturn(call)
        `when`(call.enqueue(any())).thenAnswer { invocation ->
            val callback = invocation.getArgument<Callback<NewsResponse>>(0)
            callback.onResponse(call, Response.success(mockPopularResponse))
            null
        }

        // Observe LiveData
        val observer = mock(Observer::class.java) as Observer<NewsResponse>
        viewModel.articleResults.observeForever(observer)

        // Perform the action
        viewModel.popularArticles(section, apiKey)

        // Verify results
        verify(observer).onChanged(mockPopularResponse)
    }

    @Test
    fun `test searchArticles success`(): Unit = testScope.runBlockingTest {
        val query = "trump"
        val apiKey = "Gc5uGXPTZ3noAA5ntzAjL7ZOfqBZCYP"

        // Setup mock response
        val call: Call<NYTSearchResponse> = mock(Call::class.java) as Call<NYTSearchResponse>
        `when`(apiService.searchArticles(0, query, "newest", apiKey)).thenReturn(call)
        `when`(call.enqueue(any())).thenAnswer { invocation ->
            val callback = invocation.getArgument<Callback<NYTSearchResponse>>(0)
            callback.onResponse(call, Response.success(mockSearchResponse))
            null
        }

        // Observe LiveData
        val observer = mock(Observer::class.java) as Observer<List<SearchArticle>>
        viewModel.searchArticleResults.observeForever(observer)

        // Perform the action
        viewModel.searchArticles(query, apiKey)

        // Verify results
        verify(observer).onChanged(mockSearchResponse.response.docs)
    }
}