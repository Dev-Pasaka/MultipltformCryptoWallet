package org.example.presentation.screens.dashboardScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.paste
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalClipboardManager
import cryptowallet.composeapp.generated.resources.qr_code

@Composable
fun TransferCryptoBottomSheetContent(
    onCancel: () -> Unit,
    onOpenQrScanner : () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    var address by remember {
        mutableStateOf("")
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
                .padding(bottom = 8.dp, top = 16.dp)
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
                    Row(horizontalArrangement = Arrangement.SpaceBetween,

                        verticalAlignment = Alignment.CenterVertically,

                    ){
                        AnimatedVisibility(address.isEmpty()){
                            IconButton(
                                onClick = {
                                    val text = clipboardManager.getText()
                                    address = text?.text ?: ""
                                }
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.paste),
                                    contentDescription = "Paste address",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                        AnimatedVisibility(address.isNotEmpty()){
                            IconButton(
                                onClick = {
                                    address = ""
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))

                        AnimatedVisibility(address.isEmpty()){
                            IconButton(
                                onClick = {
                                    onOpenQrScanner()
                                }
                            ) {
                                Icon(
                                    painter =  painterResource(Res.drawable.qr_code),
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()

            )
        }
        CustomNumberKeyboard {  }
    }
}