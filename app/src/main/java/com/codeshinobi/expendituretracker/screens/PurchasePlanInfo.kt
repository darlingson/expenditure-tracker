package com.codeshinobi.expendituretracker.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.codeshinobi.expendituretracker.data.ExpensesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchasePlanInfo(
    navController: NavController,
    planId: Int,
    viewModel: ExpensesViewModel = viewModel(factory = ExpensesViewModel.Factory),
) {
    val purchasePlanWithItems by viewModel.getPurchasePlanWithItems(planId)
        .collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Purchase Plan Info") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            purchasePlanWithItems?.let { planWithItems ->
                Text(
                    text = "Purchase Plan Date: ${planWithItems.purchasePlan.date}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "Total Amount: ${planWithItems.purchasePlan.totalPurchasePlanAmount}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                planWithItems.items.forEach { item ->
                    var showActualPriceInput by remember { mutableStateOf(false) }
                    var actualPrice by remember { mutableStateOf("") }

                    Card(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .clickable { showActualPriceInput = !showActualPriceInput },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Item Name: ${item.itemName}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Quantity: ${item.quantity}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Estimated Price per Item: ${item.estimatedPricePerItem}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Estimated Total: ${item.estimatedItemPriceTotal}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Purchased: ${item.isPurchased}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (item.isPurchased) Color.Green else Color.Red
                            )

                            if (showActualPriceInput) {
                                TextField(
                                    value = actualPrice,
                                    onValueChange = { actualPrice = it },
                                    label = { Text("Actual Price") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                )
                                Button(
                                    onClick = {
                                        scope.launch {
                                            viewModel.updatePurchasePrice(
                                                planId,
                                                actualPrice.toDoubleOrNull() ?: 0.0,
                                                item.id
                                            )
                                            viewModel.updatePurchasedStatus(planId, true, item.id)
                                        }
                                    },
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    Text("Mark as Purchased")
                                }
                            }
                        }
                    }
                }
            } ?: run {
                Text("Loading...", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}
