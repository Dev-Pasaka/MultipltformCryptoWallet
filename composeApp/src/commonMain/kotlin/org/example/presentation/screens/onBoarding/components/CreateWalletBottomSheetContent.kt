package org.example.presentation.screens.onBoarding.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.copy_icon
import org.example.domain.model.WalletSecrete
import org.jetbrains.compose.resources.painterResource

@Composable
fun CreateWalletBottomSheetContent(
    isWalletCreating: Boolean,
    walletSecrete: WalletSecrete,
    onDismiss: () -> Unit,
) {
    val clipboardManager = LocalClipboardManager.current
    Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp,)
                    .padding(bottom = 8.dp)
            ){
                IconButton(
                    onClick = onDismiss,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier.size(32.dp)
                    )
                }

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ){
                Text(
                    text = "Copy Your recovery code and keep it safe.",
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 48.dp)
            ){
                AnimatedVisibility(
                    visible = isWalletCreating,
                ){
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        strokeWidth = 2.dp
                    )
                }

                    Text(
                        text = walletSecrete.recoveryCode,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )
                    IconButton(
                        onClick = {
                            clipboardManager.setText(
                                annotatedString = buildAnnotatedString {
                                    append(text = walletSecrete.recoveryCode)
                                }
                            )
                        }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.copy_icon),
                            contentDescription = "Create wallet",
                            modifier = Modifier.size(32.dp)
                        )
                    }

            }
        }

}