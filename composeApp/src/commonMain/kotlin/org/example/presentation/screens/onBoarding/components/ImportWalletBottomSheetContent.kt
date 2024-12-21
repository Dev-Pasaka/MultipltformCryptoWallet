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
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.copy_icon
import org.example.domain.model.WalletSecrete
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun ImportWalletBottomSheet(
    isWalletImporting: Boolean,
    walletSecrete: WalletSecrete,
    onDismiss: () -> Unit,
    error: String? = null,
    onImportWallet: (String) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    var recoverCode by remember { mutableStateOf("") }
    var isRecoverCodeVisible by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .padding(bottom = 32.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
        ) {
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
        ) {
            Text(
                text = "Copy Your recovery code and keep it safe.",
                style = MaterialTheme.typography.titleMedium,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
        ) {
            OutlinedTextField(
                value = recoverCode,
                onValueChange = {
                    recoverCode = it
                },
                label = {
                    Text(text = "Recovery code")
                },
                singleLine = true,
                placeholder = {
                    Text(text = "Recovery code")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    textColor = MaterialTheme.colorScheme.onPrimaryContainer

                ),
                modifier = Modifier.fillMaxWidth()

            )
        }
        AnimatedVisibility(
            error?.isNotBlank() == true
        ){
            Text(
                text = error ?: "An expected error occured"
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
        ) {
            Button(
                onClick = {
                    isRecoverCodeVisible = true
                    onImportWallet(recoverCode)
                },
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Import wallet")
            }
        }
        AnimatedVisibility(isRecoverCodeVisible){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 48.dp)
            ) {
                AnimatedVisibility(
                    visible = walletSecrete.recoveryCode.isBlank() && isWalletImporting,
                ) {
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
                AnimatedVisibility(walletSecrete.recoveryCode.isNotBlank()){
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
    }

}
