package org.example.presentation.screens.onBoarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.presentation.screens.onBoarding.components.OnBoardingBottomSheet
import org.example.presentation.screens.onBoarding.components.OnBoardingScreenLowerSection
import org.example.presentation.screens.onBoarding.components.OnBoardingScreenMiddleSection
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import org.example.presentation.screens.onBoarding.components.OnBoardingRow
import org.example.presentation.screens.onBoarding.components.OnBoardingScreenUpperSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen() {
    val viewModel: OnBoardingScreenViewModel = koinViewModel()
    var bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
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
            Text(
                text = viewModel.state,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineLarge
            )
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
                actionGetStarted = {
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                }
            )

        }
    }


}