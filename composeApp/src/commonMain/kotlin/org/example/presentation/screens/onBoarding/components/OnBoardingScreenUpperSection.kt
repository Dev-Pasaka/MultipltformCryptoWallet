package org.example.presentation.screens.onBoarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun OnBoardingScreenUpperSection(
    actionRegister: () -> Unit,
    actionSignIn: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        TextButton(
            onClick = actionRegister,
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent
            )

        ) {
            Text(
                text = "Register",
                color = MaterialTheme.colorScheme.primary,
            )
        }
        TextButton(
            onClick = actionSignIn,
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent
            )

        ) {
            Text(
                text = "Sign In",
                color = MaterialTheme.colorScheme.tertiary,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}