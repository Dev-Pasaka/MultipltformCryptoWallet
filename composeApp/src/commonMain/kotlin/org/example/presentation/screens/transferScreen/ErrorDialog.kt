package org.example.presentation.screens.transferScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.example.presentation.screens.transferScreen.components.DialogBox


@Composable
fun ErrorDialog(isVisible: Boolean, onClose: () -> Unit) {
    DialogBox(
        title = "Error",
        message = "An error occurred. Please try again.",
        isVisible = isVisible,
        onClose = onClose,
        backgroundColor = Color(0xFFF44336), // Red background
        titleColor = Color.White,
        buttonColor = Color(0xFFD32F2F) // Darker red for the button
    )
}