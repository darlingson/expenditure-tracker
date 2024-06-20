package com.codeshinobi.expendituretracker

sealed class Screens(val route : String) {
    object Home : Screens("home_route")
    object Expenditures : Screens("expenditures_route")
    object ReceiptScan : Screens("receipt_scan_route")
    object ManualEntry : Screens("manual_entry_route")
    object Gallery : Screens("gallery_route")
}