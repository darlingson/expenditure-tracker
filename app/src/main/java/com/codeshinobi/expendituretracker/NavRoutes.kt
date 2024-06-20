package com.codeshinobi.expendituretracker

sealed class Screens(val route : String) {
    object Home : Screens("home_route")
    object Expenditures : Screens("expenditures_route")
}