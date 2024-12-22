package org.example.presentation.screens.dashboardScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.kmpalette.loader.rememberPainterLoader
import com.kmpalette.rememberDominantColorState
import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.bitcoin
import org.example.common.getCurrentSalutation
import org.jetbrains.compose.resources.painterResource

@Composable
fun DashboardScreenUpperSection() {

    val loader = rememberPainterLoader()
    val dominantColorState = rememberDominantColorState(loader = loader)
    val image = painterResource(Res.drawable.bitcoin)
    LaunchedEffect(Unit) {
        dominantColorState.updateFrom(image)
    }

    val backgroundGradient = Brush.radialGradient(
        colors = listOf(
            dominantColorState.color,
            MaterialTheme.colorScheme.background,
        )
    )


    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 32.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome back,",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = getCurrentSalutation(),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.size(64.dp)
                    .background(backgroundGradient)
            ){
                Image(
                    painter = painterResource(Res.drawable.bitcoin),
                    contentDescription = "Bitcoin",
                    modifier = Modifier.padding(end = 8.dp)
                        .size(48.dp)
                        .clip(shape = CircleShape)
                )
            }
        }

    }
}