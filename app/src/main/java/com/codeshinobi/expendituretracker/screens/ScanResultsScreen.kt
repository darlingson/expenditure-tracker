package com.codeshinobi.expendituretracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ScanResultsScreen(navController: NavController, recognizedText: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Scan Results")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = recognizedText)
    }
}