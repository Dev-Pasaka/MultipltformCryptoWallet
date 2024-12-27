package org.example.presentation.screens.transferScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.bitcoin
import cryptowallet.composeapp.generated.resources.eth_icon
import cryptowallet.composeapp.generated.resources.matic_icon
import cryptowallet.composeapp.generated.resources.sol_icon
import org.jetbrains.compose.resources.painterResource
import  androidx.compose.runtime.setValue
import  androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.clipRect
import kotlinx.coroutines.delay
import org.example.data.remote.dto.request.TransferCryptoReq
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TransferScreen(
    blockchain: String = "ETH-SEPOLIA",
    tokenId: String,
    address: String,
    amount: String,
    onCancel: () -> Unit
) {
    val viewModel: TransferScreenViewModel = koinViewModel()
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.statusBarsPadding()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                IconButton(
                    onClick = onCancel
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Image(
                        painter = when (blockchain) {
                            "ETH-SEPOLIA" -> painterResource(Res.drawable.eth_icon)
                            "MATIC-AMOY" -> painterResource(Res.drawable.matic_icon)
                            "SOL-DEVNET" -> painterResource(Res.drawable.sol_icon)
                            else -> painterResource(Res.drawable.bitcoin)
                        },
                        contentDescription = blockchain,
                        modifier = Modifier.size(
                            100.dp
                        ).clip(shape = CircleShape)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = blockchain,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "Amount $amount",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "Destination Address ${
                            if (address.length > 10)
                                address.substring(0, 10) + "..."
                            else address
                        }",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    LongPressButton(
                        onLongPress = {
                            viewModel.transferCrypto(
                                TransferCryptoReq(
                                    destinationAddress = address,
                                    amount = amount.toDouble(),
                                    walletId = "",
                                    idempotencyKey = "",
                                    tokenId = tokenId,
                                )
                            )
                        }
                    )
                }

            }
        }
    }
}



@Composable
fun LongPressButton(
    onLongPress: () -> Unit
) {
    var isLongPressed by remember { mutableStateOf(false) }
    var fillFraction by remember { mutableStateOf(0f) }
    val color =  MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)

    val animatedFillFraction by animateFloatAsState(
        targetValue = fillFraction,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 800)
    )

    LaunchedEffect(Unit) {
            for (i in 0..100) {
                fillFraction = i / 100f
                delay(8) // Adjust speed
            }
            fillFraction = 1f
        onLongPress()

    }

    Surface(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        isLongPressed = true
                        // Simulate the filling effect

                    },
                    onPress = {
                        tryAwaitRelease()
                        fillFraction = 0f // Reset on release
                        isLongPressed = false
                    }
                )
            },
        color = MaterialTheme.colorScheme.primary
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                clipRect {
                    drawRoundRect(
                        color = color,
                        size = size.copy(height = size.height * animatedFillFraction),
                        cornerRadius = CornerRadius(14.dp.toPx(), 14.dp.toPx())
                    )
                }
            }

            Text(
                text = if (isLongPressed) "Sending..." else "Hold to send",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

