package org.example.presentation.screens.dashboardScreen

import DashboardScreenLowerSection
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eygraber.compose.placeholder.PlaceholderHighlight
import com.eygraber.compose.placeholder.fade
import com.eygraber.compose.placeholder.material.placeholder
import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.eth_icon
import kotlinx.coroutines.launch
import org.example.domain.model.Wallet
import org.example.domain.model.WalletSecrete
import org.example.presentation.screens.dashboardScreen.components.DashboardScreenMiddleSection
import org.example.presentation.screens.dashboardScreen.components.DashboardScreenUpperSection
import org.example.presentation.screens.dashboardScreen.components.RequestCryptoBottomSheetContent
import org.example.presentation.screens.dashboardScreen.components.TransferCryptoBottomSheetContent
import org.example.presentation.screens.dashboardScreen.components.WalletItem
import org.example.presentation.screens.onBoarding.components.CreateWalletBottomSheetContent
import org.example.presentation.screens.onBoarding.components.ImportWalletBottomSheet
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(
    onNavigateToExplorer: (String) -> Unit,
    onOpenQRCodeScreen: (String, String) -> Unit,
    onNavigateToTransfer: (Double, String, String, String) -> Unit
) {
    val viewModel: DashboardScreenViewModel = koinViewModel()

    var bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            skipHiddenState = false
        )
    )
    var walletContent by remember {
        mutableStateOf(Pair("", ""))
    }
    var selectedBlockchain by remember { mutableStateOf("") }
    var selectedTokenId by remember { mutableStateOf("") }
    var walletId by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    var selectedBottomSheetContent by mutableStateOf("")

    val scrollState = rememberLazyListState()


    BottomSheetScaffold(
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = false,
        scaffoldState = bottomSheetScaffoldState,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        sheetDragHandle = {},
        sheetContent = {
            when (selectedBottomSheetContent) {
                "Request" -> RequestCryptoBottomSheetContent(
                    walletContent = walletContent,
                    onCancel = {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.hide()
                        }
                        walletContent = Pair("", "")

                    },
                    onCreateRequestLink = {
                        viewModel.createRequestLink(it)
                    },
                    createRequestLinkState = viewModel.createRequestLinkState,
                    walletId = walletId


                )

                "Send" -> TransferCryptoBottomSheetContent(
                    selectedBlockchain = selectedBlockchain,
                    selectedTokenId = selectedTokenId,
                    onOpenQrScanner = {
                        onOpenQRCodeScreen(
                            selectedBlockchain,
                            selectedTokenId
                        )
                    },
                    onCancel = {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.hide()
                        }
                    },
                    onNavigateToTransfer = onNavigateToTransfer

                )
            }
        }

    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
                .navigationBarsPadding(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                DashboardScreenUpperSection(
                    onRefresh = {
                        viewModel.refresh()
                    }
                )

                DashboardScreenMiddleSection(
                    isWalletLoading = viewModel.walletState.isLoading,
                    wallets = viewModel.walletState.wallets,
                    onNavigateToExplorer = onNavigateToExplorer,
                    onRequest = { content ->
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                            selectedBottomSheetContent = "Request"
                        }
                        walletContent = content
                    },
                    onSelectWalletId = { walletId = it },
                    onSend = { blockchain, tokenId ->
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                            selectedBottomSheetContent = "Send"
                            selectedBlockchain = blockchain
                            selectedTokenId = tokenId
                        }
                    }
                )

                DashboardScreenLowerSection(
                    isTransactionsLoading = viewModel.transactionsState.isLoading,
                    scrollState = scrollState,
                    transactions = viewModel.transactionsState.transactions,
                )
            }
        }
    }

}