package org.example.presentation.screens.onBoarding.components

import OnboardingItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.presentation.screens.onBoarding.OnBoardingData

@Composable
fun OnBoardingScreenMiddleSection(
    items: List<OnBoardingData>
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { items.size }
    )
    val scope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 8.dp
        ) { index ->
            val item = items[index]
            OnboardingItem(
                item = item
            )

        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .padding(top = 8.dp)
        ){
            items.forEachIndexed { index, item ->
                val selectedColor = MaterialTheme.colorScheme.primary
                val unselectedColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                val color = if (index == pagerState.currentPage) selectedColor else unselectedColor
                Surface(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    color = color,
                    shape = CircleShape,
                    modifier = Modifier.size(24.dp)
                ){

                }
                Spacer(modifier = Modifier.width(8.dp))

            }
        }
    }
}