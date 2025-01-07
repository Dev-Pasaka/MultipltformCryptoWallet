package org.example.presentation.screens.transferScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.data.remote.dto.request.TransferCryptoReq
import org.example.presentation.screens.transferScreen.components.ErrorDialog
import org.example.presentation.screens.transferScreen.components.SuccessDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TransferScreen(
    individualWalletId: String,
    blockchain: String = "ETH-SEPOLIA",
    tokenId: String,
    address: String,
    amount: String,
    onCancel: () -> Unit
) {
    val viewModel: TransferScreenViewModel = koinViewModel()
    var trasferDialogEvents = viewModel.transferringEvent.collectAsStateWithLifecycle(
        DialogBoxEvents(
            status = "",
            message = ""
        )
    )

    // State for showing dialogs
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    // Dialogs
    SuccessDialog(
        isVisible = showSuccessDialog,
        message = dialogMessage,
        onClose = {
            showSuccessDialog = false
            onCancel()
        }
    )
    ErrorDialog(
        isVisible = showErrorDialog,
        message = dialogMessage,
        onClose = {
            showErrorDialog = false
            onCancel()
        }
    )

    LaunchedEffect(
        key1 = trasferDialogEvents.value.status,
        key2 = trasferDialogEvents.value.message
    ) {
        when (trasferDialogEvents.value.status) {
            "Success" -> {
                showSuccessDialog = true
                dialogMessage = viewModel.transferState.message
            }

            "Error" -> {
                showErrorDialog = true
                dialogMessage = viewModel.transferState.message
            }

            else -> {}
        }

    }

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
                    Button(
                        enabled = !viewModel.transferState.isLoading,
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        onClick = {
                            viewModel.transferCrypto(
                                TransferCryptoReq(
                                    idempotencyKey = "",
                                    destinationAddress = address,
                                    amount = amount.toDouble(),
                                    walletId = individualWalletId,
                                    tokenId = tokenId
                                )
                            )
                        }
                    ) {
                        Text(
                            text = if (viewModel.transferState.isLoading) "Transferring..." else "Transfer",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

        }

    }
}






