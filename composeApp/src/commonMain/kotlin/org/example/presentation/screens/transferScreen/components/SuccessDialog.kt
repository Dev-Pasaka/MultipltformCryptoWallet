package org.example.presentation.screens.transferScreen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun SuccessDialog(isVisible: Boolean, message: String, onClose: () -> Unit) {
    DialogBox(
        title = "Success",
        message = message,
        isVisible = isVisible,
        onClose = onClose,
        backgroundColor = Color(0xFF4CAF50), // Green background
        titleColor = Color.White,
        buttonColor = Color(0xFF2E7D32) // Darker green for the button
    )
}
