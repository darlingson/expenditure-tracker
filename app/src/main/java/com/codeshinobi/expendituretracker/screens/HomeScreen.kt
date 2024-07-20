package com.codeshinobi.expendituretracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.codeshinobi.expendituretracker.data.ExpensesViewModel
import com.codeshinobi.expendituretracker.screens.components.ExpenseItem
import com.codeshinobi.expendituretracker.ui.theme.ExpenditureTrackerTheme


@Composable
fun HomeScreen(navController: NavController, viewModel: ExpensesViewModel = viewModel(factory = ExpensesViewModel.Factory)) {
    val latestExpenses by viewModel.getLatest().collectAsState(initial = emptyList())
    var totalLatestExpenses by mutableStateOf(0.0)

    // calculate total latest expenses
    latestExpenses.forEach { expense ->
        totalLatestExpenses += expense.amount
    }
    ExpenditureTrackerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
/*                horizontalAlignment = Alignment.CenterHorizontally,*/
            ) {
                Text(
                    text = "Latest Expenditures",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
                )
                Text(
                    "amount spent the last ${latestExpenses.size} times : $totalLatestExpenses",
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp, start = 16.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(latestExpenses.size) { expense ->
                        ExpenseItem(expense = latestExpenses[expense])
                    }
                }
            }
        }
    }
}