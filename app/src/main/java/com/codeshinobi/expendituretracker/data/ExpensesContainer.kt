package com.codeshinobi.expendituretracker.data

import android.content.Context
import com.codeshinobi.expendituretracker.data.repositories.BudgetsRepository
import com.codeshinobi.expendituretracker.data.repositories.ExpensesRepository
import com.codeshinobi.expendituretracker.data.repositories.PurchasePlanRepository

class ExpensesContainer(private val context: Context) {
    val expensesRepository: ExpensesRepository by lazy {
        ExpensesRepository(ExpensesDB.getExpensesDatabase(context).expensesDao())
    }
    val budgetsRepository: BudgetsRepository by lazy {
        BudgetsRepository(ExpensesDB.getExpensesDatabase(context).budgetsDao())
    }
    val purchasePlanRepository: PurchasePlanRepository by lazy {
        PurchasePlanRepository(ExpensesDB.getExpensesDatabase(context).purchasePlanDao())
    }
}

