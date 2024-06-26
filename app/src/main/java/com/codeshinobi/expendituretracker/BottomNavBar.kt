package com.codeshinobi.expendituretracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codeshinobi.expendituretracker.screens.ExpendituresScreen
import com.codeshinobi.expendituretracker.screens.GalleryScreen
import com.codeshinobi.expendituretracker.screens.HomeScreen
import com.codeshinobi.expendituretracker.screens.ManualScreen
import com.codeshinobi.expendituretracker.screens.ReceiptScanScreen
import com.codeshinobi.expendituretracker.screens.ScanResultsScreen

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
                                contentDescription = navigationItem.label
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
                ExpendituresScreen(
                    navController = navController
                )
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
        }
    }
}