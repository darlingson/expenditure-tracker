package com.codeshinobi.expendituretracker.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.codeshinobi.expendituretracker.data.ExpensesViewModel
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    navController: NavController,
    viewModel: ExpensesViewModel = viewModel(factory = ExpensesViewModel.Factory)
) {
    /**
     * This screen will show the reports of the user
     * Total items bought this month
     * Most expensive item
     * Category with most expensive item
     * Category with most items
     */

    // Get the current month's expenses
    val currentMonthExpenses by viewModel.getAllExpenses().collectAsState(initial = emptyList())
    val currentCalendar = Calendar.getInstance()
    val currentMonth = currentCalendar.get(Calendar.MONTH) // Calendar.MONTH is zero-based
    val currentYear = currentCalendar.get(Calendar.YEAR)
    val currentMonthBudget by viewModel.monthBudget(currentMonth).collectAsState(initial = null)
    val currentMonthFilteredExpenses = currentMonthExpenses.filter { expense ->
        val expenseCalendar = Calendar.getInstance().apply {
            time = Date(expense.date)
        }
        val expenseMonth = expenseCalendar.get(Calendar.MONTH)
        val expenseYear = expenseCalendar.get(Calendar.YEAR)

        expenseMonth == currentMonth && expenseYear == currentYear
    }.sortedByDescending { it.date }

    val categoriesMap = remember { mutableStateMapOf<String, Int>() }
    val categoriesMapForMaxCategoryAmount = remember { mutableStateMapOf<String, Double>() }
    val currentMonthTotalExpenses = currentMonthFilteredExpenses.sumOf { it.amount }


    categoriesMap.clear()
    categoriesMapForMaxCategoryAmount.clear()
    currentMonthFilteredExpenses.forEach { expense ->
        categoriesMap[expense.category] = categoriesMap.getOrDefault(expense.category, 0) + 1
        if (categoriesMapForMaxCategoryAmount[expense.category] == null) {
            categoriesMapForMaxCategoryAmount[expense.category] = expense.amount
        } else {
            if (expense.amount > categoriesMapForMaxCategoryAmount[expense.category]!!) {
                categoriesMapForMaxCategoryAmount[expense.category] = expense.amount
            }
        }
    }

    val maxCategoriesCount = categoriesMap.maxByOrNull { it.value }
    val totalItemsBoughtThisMonth = currentMonthFilteredExpenses.size
    val mostExpensiveItem = currentMonthFilteredExpenses.maxByOrNull { it.amount }
    val categoryWithMostExpensiveItem = categoriesMapForMaxCategoryAmount.maxByOrNull { it.value }

    Scaffold(

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
//            Text(
//                text = "Reports",
//                style = MaterialTheme.typography.headlineLarge,
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
            Text(
                text = "Total items bought this month: $totalItemsBoughtThisMonth",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            mostExpensiveItem?.let {
                Text(
                    text = "Most expensive item: ${it.name} with amount ${it.amount}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            maxCategoriesCount?.let {
                Text(
                    text = "Category with most items: ${it.key} with amount ${it.value}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            categoryWithMostExpensiveItem?.let {
                Text(
                    text = "Category with most expensive item: ${it.key} with amount ${it.value}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            currentMonthBudget?.let { budget ->
                val currentBudgetAmount = budget.budget
                val budgetDifference = currentBudgetAmount - currentMonthTotalExpenses
                if (currentBudgetAmount >= currentMonthTotalExpenses) {
                    Text(
                        text = "You are within your budget by $budgetDifference with ${getRemainingDaysOfMonth()} remaining days",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "You are over your budget by ${abs(budgetDifference)} with ${getRemainingDaysOfMonth()} remaining days",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            } ?: run {
                Text(
                    text = "Budget information is not available.\n Try to add a budget.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getRemainingDaysOfMonth(): Long {
    val today = LocalDate.now()
    val lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth())
    return ChronoUnit.DAYS.between(today, lastDayOfMonth)
}