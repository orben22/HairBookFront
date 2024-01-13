package com.example.hairbookfront.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.hairbookfront.presentation.Dimens.mediumPadding2
import com.example.hairbookfront.presentation.Dimens.pageIndicatorWidth
import com.example.hairbookfront.presentation.common.NewsButton
import com.example.hairbookfront.presentation.common.NewsTextButton
import com.example.hairbookfront.presentation.onboarding.components.OnBoardingPage
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel(),
) {
    val pagesState by viewModel.pagesState.collectAsState()
    val buttonsState by viewModel.buttonsState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(initialPage = 0) {
            pagesState.size
        }
        HorizontalPager(state = pagerState) { index ->
            OnBoardingPage(page = pagesState[index])
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = mediumPadding2)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PagerIndicator(
                modifier = Modifier.width(pageIndicatorWidth),
                pagesSize = pagesState.size,
                selectedPage = pagerState.currentPage
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                val scope = rememberCoroutineScope()

                if (buttonsState[0].isNotEmpty()) {
                    NewsTextButton(
                        text = buttonsState[0],
                        onClick = {
                            viewModel.onEvent(OnBoardingEvent.ChangePage(pagerState.currentPage - 1))
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    )
                }

                NewsButton(
                    text = buttonsState[1],
                    onClick = {
                        if (pagerState.currentPage== 2) viewModel.onEvent(OnBoardingEvent.SaveAppEntry)
                        else viewModel.onEvent(OnBoardingEvent.ChangePage(pagerState.currentPage + 1))
                        scope.launch {
                            if (pagerState.currentPage != 2) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}
