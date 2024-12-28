package org.example.presentation.screens.dashboardScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eygraber.compose.placeholder.PlaceholderHighlight
import com.eygraber.compose.placeholder.fade
import com.eygraber.compose.placeholder.material.placeholder
import com.eygraber.compose.placeholder.shimmer
import kotlinx.coroutines.launch
import org.example.domain.model.Wallet

@Composable
fun DashboardScreenMiddleSection(
    wallets: List<Wallet>,
    onNavigateToExplorer: (String) -> Unit,
    onRequest: (Pair<String, String>) -> Unit,
    onSelectWalletId: (String) -> Unit,
    onSend: (String, String) -> Unit,
    isWalletLoading: Boolean,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { wallets.size }
    )
    val scope = rememberCoroutineScope()




    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 8.dp)
            .wrapContentSize()


    ) {
        if (isWalletLoading) {
            Surface(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .height(200.dp)
                    .placeholder(
                        visible = true, color = Color.Gray,
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.White,
                        ),
                    ),
            ) {}
        } else {
            HorizontalPager(
                state = pagerState,
                pageSpacing = 8.dp,
                modifier = Modifier

            ) { index ->
                val item = wallets[index]
                WalletItem(
                    wallet = item,
                    onNavigateToExplorer = onNavigateToExplorer,
                    onRequest = onRequest,
                    onSend = onSend,
                    onSelectWalletId = onSelectWalletId
                )

            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            wallets.forEachIndexed { index, item ->
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
                ) {

                }
                Spacer(modifier = Modifier.width(8.dp))

            }
        }
    }
}