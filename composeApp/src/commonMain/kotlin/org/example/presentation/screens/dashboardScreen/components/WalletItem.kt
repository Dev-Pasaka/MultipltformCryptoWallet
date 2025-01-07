package org.example.presentation.screens.dashboardScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import com.kmpalette.loader.rememberPainterLoader
import com.kmpalette.rememberDominantColorState
import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.copy_icon
import cryptowallet.composeapp.generated.resources.explorer
import org.example.domain.model.Wallet
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WalletItem(
    wallet: Wallet,
    onNavigateToExplorer: (String) -> Unit,
    onRequest: (Pair<String, String>) -> Unit,
    onSelectWalletId: (String) -> Unit,
    onSelectIndividualWalletId: (String) -> Unit,
    onSend: (String, String) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val loader = rememberPainterLoader()
    val dominantColorState = rememberDominantColorState(loader = loader)
    val image = painterResource(wallet.icon)
    LaunchedEffect(Unit) {
        dominantColorState.updateFrom(image)
    }

    val horizontalBrush = Brush.horizontalGradient(
        colors = listOf(
            dominantColorState.color, MaterialTheme.colorScheme.primaryContainer
        )
    )



    Column() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(shape = RoundedCornerShape(16.dp)).background(brush = horizontalBrush)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Image(
                        painter = painterResource(wallet.icon),
                        contentDescription = wallet.blockchain,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(70.dp).padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = wallet.blockchain,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = wallet.walletBalance?.amount ?: "0.0",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                }
                Text(
                    text = wallet.state,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(end = 8.dp)
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(end = 8.dp, bottom = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(start = 32.dp)
                ) {
                    Text(
                        text = if (wallet.address.length > 10) wallet.address.take(10) + "..." else wallet.address,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            clipboardManager.setText(buildAnnotatedString {
                                append(wallet.address)
                            })
                        }, modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.copy_icon),
                            contentDescription = "Ethereum",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                TextButton(colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Transparent,
                ), onClick = {
                    onNavigateToExplorer(
                        wallet.explorerUrl ?: "https://sepolia.etherscan.io/address/"
                    )
                }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.explorer),
                            contentDescription = "Explore",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(32.dp)

                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Explorer",
                            textDecoration = TextDecoration.Underline,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Surface(
                enabled = wallet.walletBalance?.tokenId?.isNotBlank() == true,
                onClick = {
                    onSend(
                        wallet.blockchain,
                        wallet.walletBalance?.tokenId ?: ""
                    )
                    onSelectIndividualWalletId(wallet.individualWalletId)
                },
                shape = CircleShape,
                color = Color.Transparent
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Ethereum",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(24.dp)

                    )
                    Text(
                        text = "Send",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Surface(
                onClick = {
                    onRequest(
                        Pair(
                            wallet.blockchain, wallet.address
                        )
                    )
                    onSelectWalletId(wallet.individualWalletId)
                }, shape = CircleShape, color = Color.Transparent

            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Request",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(24.dp)

                    )
                    Text(
                        text = "Request",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }


}

