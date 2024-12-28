package org.example.presentation.screens.dashboardScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.bitcoin
import cryptowallet.composeapp.generated.resources.copy_icon
import cryptowallet.composeapp.generated.resources.eth_icon
import org.example.common.convertToLocalTime
import org.example.common.convertToSignificantString
import org.example.domain.model.Transaction
import org.jetbrains.compose.resources.painterResource

@Composable
fun TransactionItem(
    transaction: Transaction
) {
    val clipboard = LocalClipboardManager.current
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {

                    Image(
                        painter = when (transaction.blockchain) {
                            "ETH-SEPOLIA" -> painterResource(Res.drawable.eth_icon)
                            "MATIC-AMOY" -> painterResource(Res.drawable.eth_icon)
                            "SOL-DEVNET" -> painterResource(Res.drawable.eth_icon)
                            else -> painterResource(Res.drawable.bitcoin)
                        },
                        contentDescription = transaction.blockchain,
                        modifier = Modifier.size(70.dp)
                            .clip(shape = CircleShape)
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = transaction.blockchain,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = transaction.amount.convertToSignificantString(),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = transaction.status,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Sender",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 8.dp)


                ){
                    Text(
                        text = if (transaction.senderAddress.length > 24) transaction.senderAddress.take(
                            24
                        ) + "..." else transaction.senderAddress,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                    )
                    IconButton(
                        onClick = {
                            clipboard.setText(
                                AnnotatedString(
                                    transaction.senderAddress
                                )
                            )
                        },
                    ){
                        Icon(
                            painter = painterResource(Res.drawable.copy_icon),
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            contentDescription = "Copy"
                        )
                    }
                }

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(
                    text = "Receiver",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 8.dp)
                ){
                    Text(
                        text = if (transaction.receiverAddress.length > 24) transaction.receiverAddress.take(
                            24
                        ) + "..." else transaction.receiverAddress,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                    )
                    IconButton(
                        onClick = {
                            clipboard.setText(
                                AnnotatedString(
                                    transaction.receiverAddress
                                )
                            )
                        },
                    ){
                        Icon(
                            painter = painterResource(Res.drawable.copy_icon),
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            contentDescription = "Copy"
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(
                    text = "Created on",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = transaction.timestamp.convertToLocalTime(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}