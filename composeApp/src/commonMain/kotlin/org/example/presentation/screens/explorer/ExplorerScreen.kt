package org.example.presentation.screens.explorer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.multiplatform.webview.jsbridge.WebViewJsBridge
import com.multiplatform.webview.web.PlatformWebViewParams
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import cryptowallet.composeapp.generated.resources.Res
import kotlinx.coroutines.delay
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import org.example.presentation.screens.splashScreen.components.KottieLogoAnimation
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ExplorerScreen(
    url: String,
    onNavigateBack: () -> Unit
) {
    var animation by remember { mutableStateOf("") }
    var playing by remember { mutableStateOf(true) }
    val state = rememberWebViewState(
        url = url,
        //additionalHttpHeaders =
    )
    val uriHandler = LocalUriHandler.current


    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(animation)
    )
    val animationState by animateKottieCompositionAsState(
        speed = 0.25f,
        composition = composition,
        isPlaying = playing,
        iterations = 1000
    )

    LaunchedEffect(Unit) {
        println("Url: $url")
        animation = Res.readBytes("files/page_loading.json").decodeToString()
    }

    LaunchedEffect(state.isLoading) {
        delay(5000)
        if (state.isLoading){
            uriHandler.openUri(url)
            onNavigateBack()
        }
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding( vertical = 32.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ){
                IconButton(
                    onClick = onNavigateBack,

                ){
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Ethereum",
                        tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            AnimatedVisibility(state.isLoading) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 32.dp)
                ) {
                    KottieLogoAnimation(
                        animationState = animationState,
                        composition = composition,
                        modifier = Modifier.size(300.dp)
                    )
                }
            }
            WebView(
                state = state,
                webViewJsBridge = WebViewJsBridge(),
                platformWebViewParams = PlatformWebViewParams(),

            )



        }

    }
}