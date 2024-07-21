package com.codeshinobi.expendituretracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.codeshinobi.expendituretracker.data.ExpensesViewModel
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanEntity
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanItemEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPurchasePlanScreen(
    navController: NavHostController,
    viewModel: ExpensesViewModel = viewModel(factory = ExpensesViewModel.Factory)
) {
    var date by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    var itemName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var estimatedPricePerItem by remember { mutableStateOf("") }
    var estimatedItemPriceTotal by remember { mutableStateOf("") }
    var itemsList by remember { mutableStateOf(mutableListOf<PurchasePlanItemEntity>()) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Purchase Plan") },
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            val state = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
            DatePicker(state = state, modifier = Modifier.padding(16.dp))

            TextField(
                value = totalAmount,
                onValueChange = { totalAmount = it },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                label = { Text("Total Purchase Plan Amount") }
            )

            // Item fields
            TextField(
                value = itemName,
                onValueChange = { itemName = it },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                label = { Text("Item Name") }
            )
            TextField(
                value = quantity,
                onValueChange = { quantity = it },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                label = { Text("Quantity") }
            )
            TextField(
                value = estimatedPricePerItem,
                onValueChange = { estimatedPricePerItem = it },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                label = { Text("Estimated Price per Item") }
            )
            TextField(
                value = estimatedItemPriceTotal,
                onValueChange = { estimatedItemPriceTotal = it },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                label = { Text("Estimated Item Price Total") }
            )

            Button(
                onClick = {
                    val newItem = PurchasePlanItemEntity(
                        purchasePlanId = 0, // This will be set when the plan is saved
                        itemName = itemName,
                        quantity = quantity.toIntOrNull() ?: 0,
                        estimatedPricePerItem = estimatedPricePerItem.toDoubleOrNull() ?: 0.0,
                        estimatedItemPriceTotal = estimatedItemPriceTotal.toDoubleOrNull() ?: 0.0
                    )
                    itemsList.add(newItem)
                    // Clear item input fields after adding
                    itemName = ""
                    quantity = ""
                    estimatedPricePerItem = ""
                    estimatedItemPriceTotal = ""
                },
                modifier = Modifier
                    .padding(top = 16.dp)
            ) {
                Text("Add Item")
            }

            // Display added items
            itemsList.forEach { item ->
                Text(
                    text = "Item: ${item.itemName}, Quantity: ${item.quantity}, Price per Item: ${item.estimatedPricePerItem}, Total Price: ${item.estimatedItemPriceTotal}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }

            Button(
                onClick = {
                    scope.launch {
                        val newPlan = PurchasePlanEntity(
                            date = date,
                            totalPurchasePlanAmount = totalAmount.toDoubleOrNull() ?: 0.0
                        )
                        viewModel.insertPurchasePlan(newPlan, itemsList)
                        // Optionally, navigate back or clear the form after saving
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Add Purchase Plan")
            }
        }
    }
}
