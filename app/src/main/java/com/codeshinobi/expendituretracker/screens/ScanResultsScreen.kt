package com.codeshinobi.expendituretracker.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ScanResultsScreen(navController: NavController, recognizedText: String) {
    var isProcessed by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Scan Results", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        if (!isProcessed) {
            Text(text = recognizedText)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { isProcessed = true },
                modifier = Modifier
                    .padding(8.dp),
                interactionSource = remember { MutableInteractionSource() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Extract Prices")
            }
        } else {
            val prices = extractPrices(recognizedText)
            Text("Processed Prices: ${prices.size}")
            Spacer(modifier = Modifier.height(16.dp))
            prices.forEach { price ->
                Text("k $price")
            }
        }
    }
}

fun extractPrices(receiptText: String): List<String> {
    val lines = receiptText.split("\n").map { it.trim() }
    val prices = mutableListOf<String>()
    for (line in lines.reversed()) {
        val pricePattern = "\\d+\\.\\d{2}".toRegex()
        val matchResult = pricePattern.find(line)
        if (matchResult != null) {
            prices.add(matchResult.value)
        } else {
            break
        }
    }
    return prices.reversed()
}
