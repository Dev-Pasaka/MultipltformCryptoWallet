package org.example.presentation.screens.onBoarding


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material.Surface

import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.presentation.screens.onBoarding.components.OnBoardingScreenLowerSection
import org.example.presentation.screens.onBoarding.components.OnBoardingScreenMiddleSection
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import org.example.domain.model.WalletSecrete
import org.example.presentation.screens.onBoarding.components.CreateWalletBottomSheetContent
import org.example.presentation.screens.onBoarding.components.ImportWalletBottomSheet
import org.example.presentation.screens.onBoarding.components.OnBoardingRow
import org.example.presentation.screens.onBoarding.components.OnBoardingScreenUpperSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen(
    onNavigateToDashboard: () -> Unit,
) {
    val viewModel: OnBoardingScreenViewModel = koinViewModel()
    var bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            skipHiddenState = false
        )
    )
    val scope = rememberCoroutineScope()
    var selectedBottomSheetContent by mutableStateOf("")

    BottomSheetScaffold(
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = false,
        scaffoldState = bottomSheetScaffoldState,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        sheetDragHandle = {},
        sheetContent = {
            when (selectedBottomSheetContent) {
                "createWallet" -> {
                    CreateWalletBottomSheetContent(
                        isWalletCreating = viewModel.createWalletState.isLoading,
                        walletSecrete = WalletSecrete(
                            recoverCode = viewModel.createWalletState.recoverCode ?: "",
                        ),
                        onDismiss = {
                            scope.launch {
                                bottomSheetScaffoldState.bottomSheetState.hide()
                            }
                            onNavigateToDashboard()
                        }
                    )
                }
                "importWallet" -> {
                    ImportWalletBottomSheet(
                        isWalletImporting = false,
                        walletSecrete = WalletSecrete(
                            recoverCode =  viewModel.importWalletState.recoverCode ?: "",
                        ),
                        onDismiss = {
                            scope.launch {
                                bottomSheetScaffoldState.bottomSheetState.hide()
                            }
                            onNavigateToDashboard()
                        },
                        onImportWallet = {
                            viewModel.importWallet(it)
                        }
                    )
                }
                else -> {
                }
            }
        }

    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                OnBoardingScreenUpperSection(
                    actionRegister = {

                    },
                    actionSignIn = {

                    }
                )
                OnBoardingScreenMiddleSection(
                    items = viewModel.onBoardingData
                )
                OnBoardingScreenLowerSection(
                    onCreateWallet = {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                        viewModel.createWallet()
                        selectedBottomSheetContent = "createWallet"
                    },
                    onImportWallet = {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                        selectedBottomSheetContent = "importWallet"
                    }
                )

            }
        }

    }

}