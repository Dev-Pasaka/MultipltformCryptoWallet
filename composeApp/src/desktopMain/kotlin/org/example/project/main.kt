package org.example.project

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import org.example.di.initKoin

fun main(){
    initKoin()
    application {
        Window(
            resizable = false,
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