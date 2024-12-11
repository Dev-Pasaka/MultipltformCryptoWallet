package org.example.project


import androidx.compose.runtime.*

import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview


import org.example.presentation.navigation.NavGraph
import org.example.presentation.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()
        NavGraph(
            navController = navController
        )
    }
}