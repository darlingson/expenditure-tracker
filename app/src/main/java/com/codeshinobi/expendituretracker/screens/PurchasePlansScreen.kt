package com.codeshinobi.expendituretracker.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.codeshinobi.expendituretracker.Screens
import com.codeshinobi.expendituretracker.data.ExpensesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchasePlansScreen(navController: NavHostController, viewModel: ExpensesViewModel) {
    val purchasePlans by viewModel.getAllPurchasePlans().collectAsState(initial = emptyList())


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screens.addPurchasePlanScreen.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        },
        content = { innerPadding ->
            Spacer(modifier = Modifier
                .height(16.dp)
                .padding(innerPadding))

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
                            .padding(vertical = 8.dp).clickable{
                                navController.navigate("purchase_plan_info/${planWithItems.purchasePlan.id}")
                            },
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

