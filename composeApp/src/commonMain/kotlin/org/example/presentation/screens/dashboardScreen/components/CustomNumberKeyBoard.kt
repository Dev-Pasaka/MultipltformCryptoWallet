package org.example.presentation.screens.dashboardScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.backspace
import org.jetbrains.compose.resources.painterResource

@Composable
fun CustomNumberKeyboard(
    onDone: (String) -> Unit
) {
    var enteredNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Label to display the entered number
        Text(
            text = enteredNumber.ifEmpty { "Enter Amount" },
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        )

        // Number buttons
        val buttons = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf(".", "0", "<")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { button ->
                    NumberButton(
                        label = button,
                        onClick = {
                            when (button) {
                                "<" -> if (enteredNumber.isNotEmpty()) {
                                    enteredNumber = enteredNumber.dropLast(1)
                                }
                                "." -> if (!enteredNumber.contains(".")) {
                                    enteredNumber += "."
                                }
                                else -> if (enteredNumber.length < 10) {
                                    enteredNumber += button
                                }
                            }
                        }
                    )
                }
            }
        }

        // Done button
        IconButton(
            onClick = { onDone(enteredNumber) },
            modifier = Modifier
                .padding(top = 16.dp)
                .size(64.dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "Done",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NumberButton(
    label: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = Color.LightGray
    ) {
        Box(
            modifier = Modifier
                .size(64.dp),
            contentAlignment = Alignment.Center
        ) {
            if (label == "<") {
                Icon(
                    painter = painterResource(Res.drawable.backspace),
                    contentDescription = "Delete",
                    tint = Color.Black
                )
            } else {
                Text(text = label, fontSize = 20.sp, color = Color.Black)
            }
        }
    }
}
