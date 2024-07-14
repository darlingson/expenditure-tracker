package com.codeshinobi.expendituretracker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BalanceScale
import compose.icons.fontawesomeicons.solid.ChartBar

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {

    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Screens.Home.route
            ),
            BottomNavigationItem(
                label = "Expenditures",
                icon = Icons.Filled.ShoppingCart,
                route = Screens.Expenditures.route
            ),
            BottomNavigationItem(
                label = "Budgets",
                icon = FontAwesomeIcons.Solid.BalanceScale,
                route = Screens.ReceiptScan.route
            ),
            BottomNavigationItem(
                label = "Reports",
                icon = FontAwesomeIcons.Solid.ChartBar,
                route = Screens.Reports.route
            ),
        )
    }
}