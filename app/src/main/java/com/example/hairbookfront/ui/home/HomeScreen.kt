package com.example.hairbookfront.ui.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hairbookfront.ui.common.EmptyStateComponent
import com.example.hairbookfront.ui.common.Loader
import com.example.hairbookfront.ui.common.NewsRowComponent
import com.example.hairbookfront.util.Constants.HOME_SCREEN_TAG
import com.example.hairbookfront.util.ResourceState


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(hairBookViewModel: HomeViewModel = hiltViewModel()) {
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
                Log.d(HOME_SCREEN_TAG, "HomeScreen: LOADING")
                Loader()
            }

            is ResourceState.SUCCESS -> {
                Log.d(HOME_SCREEN_TAG, "HomeScreen: SUCCESS")
                val response = (newsResponse.value as ResourceState.SUCCESS).data
                Log.d(
                    HOME_SCREEN_TAG,
                    "HomeScreen: Status:${response.status} Result length:${response.totalResults}"
                )
                if (response.articles.isNotEmpty()) {
                    NewsRowComponent(page, response.articles[page])
                } else {
                    EmptyStateComponent()
                }
            }

            is ResourceState.ERROR -> {
                Log.d(HOME_SCREEN_TAG, "HomeScreen: ERROR")
                val error = (newsResponse.value as ResourceState.ERROR)
                Log.d(HOME_SCREEN_TAG, "HomeScreen: $error")
            }
        }
    }
}
