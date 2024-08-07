package com.codeshinobi.expendituretracker.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.codeshinobi.expendituretracker.Screens
import com.codeshinobi.expendituretracker.data.ExpensesViewModel
import com.codeshinobi.expendituretracker.screens.components.ExpenseItem
import com.codeshinobi.expendituretracker.ui.theme.ExpenditureTrackerTheme
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.Keyboard
import compose.icons.fontawesomeicons.solid.CameraRetro
import compose.icons.fontawesomeicons.solid.PhotoVideo
import java.util.Calendar
import java.util.Date

data class ExpendituresGridItemData(
    val name: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddNewExpendituresScreen(navController: NavController) {
    ExpenditureTrackerTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Add New Expenditure") },
                    navigationIcon = {
                        if (navController.previousBackStackEntry != null) {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    }
                )
            }
        ){
        Surface(
            modifier = Modifier.fillMaxSize().padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            val items = listOf(
                ExpendituresGridItemData(
                    name = "Scan Receipt",
                    icon = FontAwesomeIcons.Solid.CameraRetro,
                    onClick = { navController.navigate(Screens.ReceiptScan.route) }
                ),
                ExpendituresGridItemData(
                    name = "Gallery",
                    icon = FontAwesomeIcons.Solid.PhotoVideo,
                    onClick = { navController.navigate(Screens.Gallery.route) }
                ),
                ExpendituresGridItemData(
                    name = "Enter manually",
                    icon = FontAwesomeIcons.Regular.Keyboard,
                    onClick = { navController.navigate(Screens.ManualEntry.route) }
                )
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 120.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(items.size) { index ->
                        val item = items[index]
                        ExpenditureGridItem(
                            name = item.name,
                            icon = item.icon,
                            onClick = item.onClick
                        )
                    }
                }
            }
        }
    }}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenditureGridItem(
    modifier: Modifier = Modifier,
    name: String,
    icon: ImageVector,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                    )
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.secondary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = name,
                        modifier = Modifier.fillMaxSize(),
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.2f),
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    ),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun ExpendituresTabScreen(navController: NavHostController) {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Current Month", /*"Add New",*/ "Previous Months")
    val viewModel: ExpensesViewModel = viewModel(factory = ExpensesViewModel.Factory)
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate(Screens.addScreen.route) }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        icon = {
                            when (index) {
                                0 -> Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null
                                )
                                /*1 -> Icon(imageVector = Icons.Default.Info, contentDescription = null)*/
                                1 -> Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            }
            when (tabIndex) {
                0 -> CurrentMonthScreen(viewModel)
                /*            1 -> AddNewExpendituresScreen(navController)*/
                1 -> PreviousMonthsScreen(viewModel)
            }
        }
    }
}

@Composable
fun CurrentMonthScreen(
    viewModel: ExpensesViewModel = viewModel(factory = ExpensesViewModel.Factory)
) {
    val currentMonthExpenses by viewModel.getAllExpenses().collectAsState(initial = emptyList())
    val currentCalendar = Calendar.getInstance()
    val currentMonth = currentCalendar.get(Calendar.MONTH) // Calendar.MONTH is zero-based
    val currentYear = currentCalendar.get(Calendar.YEAR)
    val currentMonthFilteredExpenses = currentMonthExpenses.filter { expense ->
        val expenseCalendar = Calendar.getInstance().apply {
            time = Date(expense.date)
        }
        val expenseMonth = expenseCalendar.get(Calendar.MONTH)
        val expenseYear = expenseCalendar.get(Calendar.YEAR)

        expenseMonth == currentMonth && expenseYear == currentYear
    }.sortedByDescending { it.date }
    ExpenditureTrackerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Current Month Expenditure",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(currentMonthFilteredExpenses.size) { expense ->
                            ExpenseItem(expense = currentMonthFilteredExpenses[expense])
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun PreviousMonthsScreen(viewModel: ExpensesViewModel = viewModel(factory = ExpensesViewModel.Factory)) {
    val previousMonthsExpenses by viewModel.getAllExpenses().collectAsState(initial = emptyList())
    val currentCalendar = Calendar.getInstance()
    val currentMonth = currentCalendar.get(Calendar.MONTH) // Calendar.MONTH is zero-based
    val currentYear = currentCalendar.get(Calendar.YEAR)
    val previousMonthsFilteredExpenses = previousMonthsExpenses.filter { expense ->
        val expenseCalendar = Calendar.getInstance().apply {
            time = Date(expense.date)
        }
        val expenseMonth = expenseCalendar.get(Calendar.MONTH)
        val expenseYear = expenseCalendar.get(Calendar.YEAR)
        (expenseYear < currentYear) || (expenseYear == currentYear && expenseMonth < currentMonth)
    }
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
                    text = "Previous Months Expenditure",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(previousMonthsFilteredExpenses.size) { expense ->
                        ExpenseItem(expense = previousMonthsFilteredExpenses[expense])
                    }
                }
            }
        }
    }
}

