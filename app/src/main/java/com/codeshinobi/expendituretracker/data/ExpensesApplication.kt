package com.codeshinobi.expendituretracker.data

import android.app.Application

class ExpensesApplication : Application() {
    lateinit var expensesContainer: ExpensesContainer
    override fun onCreate() {
        super.onCreate()
        expensesContainer = ExpensesContainer(this)
    }
}