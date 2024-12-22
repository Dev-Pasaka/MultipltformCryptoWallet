package org.example.presentation.screens.dashboardScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.bitcoin
import cryptowallet.composeapp.generated.resources.eth_icon
import cryptowallet.composeapp.generated.resources.matic_icon
import cryptowallet.composeapp.generated.resources.sol_icon
import org.example.common.painterWithBackground
import org.jetbrains.compose.resources.painterResource
import qrgenerator.qrkitpainter.PatternType
import qrgenerator.qrkitpainter.QrBallType
import qrgenerator.qrkitpainter.QrFrameType
import qrgenerator.qrkitpainter.QrKitBrush
import qrgenerator.qrkitpainter.QrKitColors
import qrgenerator.qrkitpainter.QrKitLogo
import qrgenerator.qrkitpainter.QrKitShapes
import qrgenerator.qrkitpainter.QrPixelType
import qrgenerator.qrkitpainter.customBrush
import qrgenerator.qrkitpainter.getSelectedFrameShape
import qrgenerator.qrkitpainter.getSelectedPattern
import qrgenerator.qrkitpainter.getSelectedPixel
import qrgenerator.qrkitpainter.getSelectedQrBall
import qrgenerator.qrkitpainter.rememberQrKitPainter
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalClipboardManager
import cryptowallet.composeapp.generated.resources.paste
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

@Composable
fun RequestCryptoBottomSheetContent(
    walletContent: Pair<String, String>,
    onCancel: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    var address by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    var qrData by remember { mutableStateOf("") }

    val centerLogo = when (walletContent.first) {
        "ETH-SEPOLIA" -> painterWithBackground(
            basePainter = painterResource(Res.drawable.eth_icon),
            backgroundColor = Color.White,
            cornerRadius = 100.dp
        )

        "MATIC-AMOY" -> painterWithBackground(
            basePainter = painterResource(Res.drawable.matic_icon),
            backgroundColor = Color.White,
            cornerRadius = 100.dp
        )

        "SOL-DEVNET" -> painterWithBackground(
            basePainter = painterResource(Res.drawable.sol_icon),
            backgroundColor = Color.White,
            cornerRadius = 100.dp
        )

        else -> painterWithBackground(
            basePainter = painterResource(Res.drawable.bitcoin),
            backgroundColor = Color.White,
            cornerRadius = 100.dp
        )
    }


    val qrCodeColor = MaterialTheme.colorScheme.onPrimaryContainer
    val painter = rememberQrKitPainter(qrData) {
        shapes = QrKitShapes(
            ballShape = getSelectedQrBall(QrBallType.SquareQrBall()),
            darkPixelShape = getSelectedPixel(QrPixelType.CirclePixel()),
            frameShape = getSelectedFrameShape(QrFrameType.RoundCornersFrame(0.3f)),
            codeShape = getSelectedPattern(PatternType.SquarePattern),
        )
        colors = QrKitColors(
            darkBrush = QrKitBrush.customBrush {
                Brush.linearGradient(
                    0f to qrCodeColor,
                    1f to qrCodeColor,
                    end = Offset(it, it)
                )
            }
        )
        logo = QrKitLogo(centerLogo)
    }

    LaunchedEffect(
        key1 = amount,
        key2 = address
    ) {
        if (amount.isNotEmpty() || address.isNotEmpty()) {
            val qrMap = mapOf(
                "address" to address,
                "amount" to amount
            )
            qrData = Json.encodeToString(
                MapSerializer(String.serializer(), String.serializer()),
                qrMap
            )
            println("Qr data: $qrData")
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .padding(bottom = 32.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 8.dp, top = 8.dp)
        ) {
            IconButton(
                onClick = onCancel,

                ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(32.dp)
                )
            }

        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            OutlinedTextField(
                value = address,
                onValueChange = {
                    address = it
                },
                label = {
                    Text(text = "Address")
                },
                singleLine = true,
                placeholder = {
                    Text(text = "O0xaa655...")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    textColor = MaterialTheme.colorScheme.onPrimaryContainer

                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            val text = clipboardManager.getText()
                            address = text?.text ?: ""
                        }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.paste),
                            contentDescription = "Create wallet",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()

            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = {
                    if (it.all { char -> char.isDigit() || char == '.' }) {
                        amount = it
                    }
                },
                label = {
                    Text(text = "Amount")
                },
                singleLine = true,
                placeholder = {
                    Text(text = "Amount")
                },
                keyboardOptions = KeyboardOptions.Default,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    textColor = MaterialTheme.colorScheme.onPrimaryContainer

                ),
                modifier = Modifier.fillMaxWidth()

            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(200.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.size(200.dp)
                    .padding(horizontal = 16.dp)

            ) {
                Text(
                    text = "Amount Requested",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = if (amount.isNotEmpty()) amount else "0.0",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = "Request",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Ethereum",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}