package com.codeshinobi.expendituretracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.codeshinobi.expendituretracker.data.ExpensesViewModel
@Composable
fun BudgetsScreen(navController: NavHostController) {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Current Month", "Add New", "Previous Months")
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
                            2 -> Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                        }
                    }
                )
            }
        }
        when (tabIndex) {
            0 -> CurrentMonthScreen( viewModel)
            1 -> AddNewExpendituresScreen(navController)
            2 -> PreviousMonthsScreen( viewModel)
        }
    }
}
@Composable
fun CurrentMonthBudgetOverView(
    viewModel: ExpensesViewModel = viewModel(factory = ExpensesViewModel.Factory)
)
{
}
@Composable
fun PreviousMonthBudgets(
    viewModel: ExpensesViewModel = viewModel(factory = ExpensesViewModel.Factory)
)
{
}