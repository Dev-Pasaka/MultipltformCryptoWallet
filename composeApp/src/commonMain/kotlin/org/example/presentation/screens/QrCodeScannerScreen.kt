package org.example.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import qrscanner.CameraLens
import qrscanner.QrScanner

@Composable
fun QrCodeScannerScreen(
    onNavigateBack: () -> Unit
) {
    var qrCodeURL by remember { mutableStateOf("") }
    var flashlightOn by remember { mutableStateOf(false) }
    var openImagePicker by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = Modifier.fillMaxSize()
        .navigationBarsPadding()
        .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(), // Fill the entire screen
                verticalArrangement = Arrangement.Center, // Center vertically
                horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize() // Adjust the size of the QR scanner view
                        .clip(RoundedCornerShape(14.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    QrScanner(
                        modifier = Modifier
                            .fillMaxSize(), // Ensure the scanner respects rounded corners
                        flashlightOn = flashlightOn,
                        openImagePicker = openImagePicker,
                        onCompletion = { qrCodeURL = it
                                       println(it)
                                       },
                        imagePickerHandler = { openImagePicker = it },
                        cameraLens = CameraLens.Back,
                        onFailure = { error ->
                            coroutineScope.launch {
                                // Handle QR code error
                            }
                        }
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.TopCenter)
            ){
                IconButton(
                    onClick = onNavigateBack
                ){
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }

    // Optional Snackbar
    if (qrCodeURL.isNotEmpty()) {
        // Show qrCodeURL in a Snackbar or handle it as necessary
        LaunchedEffect(qrCodeURL) {
            snackbarHostState.showSnackbar("Scanned URL: $qrCodeURL")
        }
    }

    // Show Snackbar if necessary
    SnackbarHost(hostState = snackbarHostState)
}





