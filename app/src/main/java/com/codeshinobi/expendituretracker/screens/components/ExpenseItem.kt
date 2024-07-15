package com.codeshinobi.expendituretracker.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeshinobi.expendituretracker.data.entities.Expense

@Composable
fun ExpenseItem(expense: Expense) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = expense.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Amount: $${expense.amount}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Date: ${expense.date}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Category: ${expense.category}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Note: ${expense.note}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
