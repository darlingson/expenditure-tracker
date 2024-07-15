package com.codeshinobi.expendituretracker.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codeshinobi.expendituretracker.data.entities.Expense
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ExpenseItem(expense: Expense) {
    var isExpanded by remember { mutableStateOf(false) }

    val originalFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
    val targetFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH)
    val date: Date = originalFormat.parse(expense.date) ?: Date()
    val formattedDate = targetFormat.format(date)
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }, // Toggle expansion on click
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = formattedDate, style = MaterialTheme.typography.bodyMedium)
                        Text(text = "K ${expense.amount}", style = MaterialTheme.typography.bodyMedium, color = Color.Green)
                    }

                    Text(text = expense.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    modifier = Modifier.size(24.dp)
                )
            }

            if (isExpanded) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text(text = "Category: ${expense.category}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Note: ${expense.note}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}