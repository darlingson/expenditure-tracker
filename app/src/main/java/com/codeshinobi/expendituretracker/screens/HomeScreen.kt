package com.codeshinobi.expendituretracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val categoriesMap = remember { mutableStateMapOf<String, Int>() }
    // calculate total latest expenses and categories
    categoriesMap.clear()
    latestExpenses.forEach { expense ->
        totalLatestExpenses += expense.amount
        categoriesMap[expense.category] = categoriesMap.getOrDefault(expense.category, 0) + 1
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
                LazyRow(
                    userScrollEnabled = true,
                ){
                    itemsIndexed(categoriesMap.toList()){ index, category ->
                        CategoryItem(category = category.first, amount = category.second)
                    }
                }
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
@Composable
fun CategoryItem(category: String, amount: Int) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier = Modifier.padding(16.dp)
            .border(2.dp, MaterialTheme.colorScheme.primary, shape)
            .background(MaterialTheme.colorScheme.inversePrimary, shape)
            .padding(8.dp)
    ){
        Text(
            text = "$category : $amount",
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp, start = 16.dp)
        )
    }

}