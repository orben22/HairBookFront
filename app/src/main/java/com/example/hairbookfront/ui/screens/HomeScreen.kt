package com.example.hairbookfront.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hairbookfront.ui.components.EmptyStateComponent
import com.example.hairbookfront.ui.components.Loader
import com.example.hairbookfront.ui.components.NewsRowComponent
import com.example.hairbookfront.ui.viewmodel.HairBookViewModel
import com.example.utilities.ResourceState

const val TAG = "HomeScreen"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(hairBookViewModel: HairBookViewModel = hiltViewModel()) {
    val newsResponse = hairBookViewModel.news.collectAsState()


    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { 100 }
    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        pageSize = PageSize.Fill,
        pageSpacing = 15.dp
    ) { page: Int ->
        when (newsResponse.value) {
            is ResourceState.LOADING -> {
                Log.d(TAG, "HomeScreen: LOADING")
                Loader()
            }

            is ResourceState.SUCCESS -> {
                Log.d(TAG, "HomeScreen: SUCCESS")
                val response = (newsResponse.value as ResourceState.SUCCESS).data
                Log.d(
                    TAG,
                    "HomeScreen: Status:${response.status} Result length:${response.totalResults}"
                )
                if (response.articles.isNotEmpty()) {
                    NewsRowComponent(page, response.articles[page])
                } else {
                    EmptyStateComponent()
                }
            }

            is ResourceState.ERROR -> {
                Log.d(TAG, "HomeScreen: ERROR")
                val error = (newsResponse.value as ResourceState.ERROR)
                Log.d(TAG, "HomeScreen: $error")
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}