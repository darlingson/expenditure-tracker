package com.codeshinobi.expendituretracker.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.codeshinobi.expendituretracker.data.ExpensesViewModel
import com.codeshinobi.expendituretracker.data.entities.Budget
import com.codeshinobi.expendituretracker.ui.theme.ExpenditureTrackerTheme
import java.util.Calendar
import java.util.Date

@Composable
fun BudgetsScreen(navController: NavHostController) {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Overview",  "Previous Months")
    val viewModel: ExpensesViewModel = viewModel(factory = ExpensesViewModel.Factory)
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    icon = {
                        when (index) {
                            0 -> Icon(imageVector = Icons.Default.Home, contentDescription = null)
                            1 -> Icon(imageVector = Icons.Default.Info, contentDescription = null)
                        }
                    }
                )
            }
        }
        when (tabIndex) {
            0 -> CurrentMonthBudgetOverView( viewModel)
            1 -> PreviousMonthBudgets( viewModel)
        }
    }
}
@Composable
fun CurrentMonthBudgetOverView(
    viewModel: ExpensesViewModel = viewModel(factory = ExpensesViewModel.Factory)
)
{
    val currentCalendar = Calendar.getInstance()
    val currentMonth = currentCalendar.get(Calendar.MONTH) // Calendar.MONTH is zero-based
    val currentYear = currentCalendar.get(Calendar.YEAR)

    val currentMonthExpenses by viewModel.getAllExpenses().collectAsState(initial = emptyList())
    val currentMonthBudget by viewModel.monthBudget(currentMonth).collectAsState(initial = null)
    val showDialog = remember { mutableStateOf(false) }

    val currentMonthFilteredExpenses = currentMonthExpenses.filter { expense ->
        val expenseCalendar = Calendar.getInstance().apply {
            time = Date(expense.date)
        }
        val expenseMonth = expenseCalendar.get(Calendar.MONTH)
        val expenseYear = expenseCalendar.get(Calendar.YEAR)

        expenseMonth == currentMonth && expenseYear == currentYear
    }
    val expensesTotal = currentMonthFilteredExpenses.sumOf { it.amount }


    ExpenditureTrackerTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(onClick = { showDialog.value = true }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "add")
                }
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                if (currentMonthBudget != null) {
                    BudgetItem(currentMonthBudget!!)
                }
                Text(
                    text = "Current Total Expenditure : $expensesTotal",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
//                Text(
//                    text = "$expensesTotal",
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
//                )
            }
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Add Budget for Current Month") },
                    text = {
                        AddBudgetForm(
                            onBudgetAdded = { budget ->
                                viewModel.insertBudget(budget)
                                showDialog.value = false
                            }
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {  }
                        ) {
                            Text("Add Budget Confirm")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { showDialog.value = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}
@Composable
fun AddBudgetForm(onBudgetAdded: (Budget) -> Unit) {
    var amount by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("") }

    val time = System.currentTimeMillis()
    var id: Int = time.toInt()
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Budget Amount") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Year") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = month,
            onValueChange = { month = it },
            label = { Text("Month") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val budget = Budget(
                    budget = amount.toDouble(),
                    year = year.toInt(),
                    month = month.toInt(),
                )
                onBudgetAdded(budget)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Add Budget")
        }
    }

}
@Composable
fun PreviousMonthBudgets(
    viewModel: ExpensesViewModel = viewModel(factory = ExpensesViewModel.Factory)
) {
    // Collect the list of budgets from the ViewModel
    val allBudgets by viewModel.getAllBudgets().collectAsState(initial = emptyList())
val context = LocalContext.current
    ExpenditureTrackerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "All Budgets",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(allBudgets.size) { index ->
                        val budget = allBudgets[index]
                        BudgetItem(budget)
                    }
                }
            }
        }
    }
}

@Composable
fun BudgetItem(budget: Budget) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Month: ${budget.month}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Amount: ${budget.budget}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}