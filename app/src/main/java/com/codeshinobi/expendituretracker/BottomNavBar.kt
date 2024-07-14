package com.codeshinobi.expendituretracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codeshinobi.expendituretracker.screens.ExpendituresTabScreen
import com.codeshinobi.expendituretracker.screens.GalleryScreen
import com.codeshinobi.expendituretracker.screens.HomeScreen
import com.codeshinobi.expendituretracker.screens.ReceiptScanScreen
import com.codeshinobi.expendituretracker.screens.RecognizeResult
import com.codeshinobi.expendituretracker.screens.ScanResultsScreen
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar() {
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems().forEachIndexed {index,navigationItem ->
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues)) {
            composable(Screens.Home.route) {
                HomeScreen(
                    navController = navController
                )
            }
            composable(Screens.Expenditures.route) {
                ExpendituresTabScreen(
                    navController = navController
                )
            }
            composable(Screens.Budgets.route) {
                Text("Budgets Screen")
            }
            composable(Screens.Reports.route) {
                Text("Reports Screen")
            }
            composable(Screens.ReceiptScan.route) {
                ReceiptScanScreen(
                    navController = navController
                )
            }
            composable(Screens.Gallery.route) {
                GalleryScreen(
                    navController = navController
                )
            }
            composable(Screens.ScanResults.route) { backStackEntry ->
                val text = backStackEntry.arguments?.getString("text")
                if (text != null) {
                    ScanResultsScreen(navController, text)
                }
            }
            composable("text_recon/{text}"){navBackStackEntry ->
                val gson: Gson = GsonBuilder().create()
                val recogJson = navBackStackEntry.arguments?.getString("text")
                val recogObject = gson.fromJson(recogJson, RecognizeResult::class.java)
                recogObject?.let { recog ->
                    ScanResultsScreen(navController, recog.text)
                }
            }
        }
    }
}