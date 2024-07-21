package com.codeshinobi.expendituretracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.codeshinobi.expendituretracker.data.ExpensesViewModel
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanEntity
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanItemEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchasePlansScreen(navController: NavHostController, viewModel: ExpensesViewModel) {
    val purchasePlans by viewModel.getAllPurchasePlans().collectAsState(initial = emptyList())
    var date by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    var itemName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var estimatedPricePerItem by remember { mutableStateOf("") }
    var estimatedItemPriceTotal by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Scaffold(
        content = { innerPadding ->
                Column(modifier = Modifier.fillMaxWidth().padding(innerPadding).verticalScroll(rememberScrollState())) {
                    val state = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
                    DatePicker(state = state, modifier = Modifier.padding(16.dp))
                    TextField(
                        value = totalAmount,
                        onValueChange = { totalAmount = it },
                        modifier = Modifier
                            .background(Color.Gray.copy(alpha = 0.2f))
                            .padding(8.dp)
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = { Text("Total Purchase Plan Amount") }
                    )
                    TextField(
                        value = itemName,
                        onValueChange = { itemName = it },
                        modifier = Modifier
                            .background(Color.Gray.copy(alpha = 0.2f))
                            .padding(8.dp)
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = { Text("Item Name") }
                    )
                    TextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        modifier = Modifier
                            .background(Color.Gray.copy(alpha = 0.2f))
                            .padding(8.dp)
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = { Text("Quantity") }
                    )
                    TextField(
                        value = estimatedPricePerItem,
                        onValueChange = { estimatedPricePerItem = it },
                        modifier = Modifier
                            .background(Color.Gray.copy(alpha = 0.2f))
                            .padding(8.dp)
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = { Text("Estimated Price per Item") }
                    )
                    TextField(
                        value = estimatedItemPriceTotal,
                        onValueChange = { estimatedItemPriceTotal = it },
                        modifier = Modifier
                            .background(Color.Gray.copy(alpha = 0.2f))
                            .padding(8.dp)
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        label = { Text("Estimated Item Price Total") }
                    )
                    Button(
                        onClick = {
                            scope.launch {
                                val newPlan = PurchasePlanEntity(
                                    date = date,
                                    totalPurchasePlanAmount = totalAmount.toDoubleOrNull() ?: 0.0
                                )
                                val newItem = PurchasePlanItemEntity(
                                    purchasePlanId = 0,
                                    itemName = itemName,
                                    quantity = quantity.toIntOrNull() ?: 0,
                                    estimatedPricePerItem = estimatedPricePerItem.toDoubleOrNull() ?: 0.0,
                                    estimatedItemPriceTotal = estimatedItemPriceTotal.toDoubleOrNull() ?: 0.0
                                )
                                viewModel.insertPurchasePlan(newPlan, listOf(newItem))
                            }
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Add Purchase Plan")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Purchase Plans List",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Display purchase plans
                LazyColumn {
                    items(purchasePlans) { planWithItems ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.elevatedCardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Date: ${planWithItems.purchasePlan.date}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "Total Amount: ${planWithItems.purchasePlan.totalPurchasePlanAmount}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                planWithItems.items.forEach { item ->
                                    Text(
                                        text = "Item: ${item.itemName}, Quantity: ${item.quantity}, Total: ${item.estimatedItemPriceTotal}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }

        })
}

