package com.codeshinobi.expendituretracker

sealed class Screens(val route: String) {
    object Home : Screens("home_route")
    object Expenditures : Screens("expenditures_route")
    object ReceiptScan : Screens("receipt_scan_route")
    object ManualEntry : Screens("manual_entry_route")
    object Gallery : Screens("gallery_route")
    object Reports : Screens("reports_route")
    object Budgets : Screens("budgets_route")
    object ScanResults : Screens("scan_results_route") {
        fun createRoute(text: String): String {
            return "scan_results_route/$text"
        }
    }
}