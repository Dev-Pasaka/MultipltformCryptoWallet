package org.example.presentation.screens.transferScreen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun ErrorDialog(isVisible: Boolean, message: String, onClose: () -> Unit) {
    DialogBox(
        title = "Error",
        message = message,
        isVisible = isVisible,
        onClose = onClose,
        backgroundColor = Color(0xFFF44336), // Red background
        titleColor = Color.White,
        buttonColor = Color(0xFFD32F2F) // Darker red for the button
    )
}