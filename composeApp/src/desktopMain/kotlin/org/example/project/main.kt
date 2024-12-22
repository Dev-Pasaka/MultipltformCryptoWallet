package org.example.project

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import dev.datlag.kcef.KCEFBuilder.Download
import dev.datlag.kcef.KCEFBuilder.Download.Builder
import kotlinx.coroutines.runBlocking
import org.example.di.initKoin
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import dev.datlag.kcef.KCEF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.max

fun main() = runBlocking {
    initKoin()
    application {
        Window(
            resizable = true,
            state = WindowState(
                width = 450.dp,
                height = 900.dp
            ),
            onCloseRequest = ::exitApplication,
            title = "SmartPesa",
        ) {
            App()
        }
    }
}