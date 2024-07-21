package com.codeshinobi.expendituretracker.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.codeshinobi.expendituretracker.data.entities.Budget
import com.codeshinobi.expendituretracker.data.entities.Expense
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanEntity
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanItemEntity
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanWithItems
import com.codeshinobi.expendituretracker.data.repositories.BudgetsRepository
import com.codeshinobi.expendituretracker.data.repositories.ExpensesRepository
import com.codeshinobi.expendituretracker.data.repositories.PurchasePlanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ExpensesViewModel(
    private val expensesRepository: ExpensesRepository,
    private val budgetsRepository: BudgetsRepository,
    private val purchasePlanRepository: PurchasePlanRepository
) : ViewModel() {
    fun getAllExpenses(): Flow<List<Expense>> = expensesRepository.getAll()
    fun getLatest() = expensesRepository.getLatest()
    fun getAllBudgets(): Flow<List<Budget>> = budgetsRepository.getAll()
    fun insertExpense(expense: Expense) = viewModelScope.launch {
        expensesRepository.insert(expense)
    }
    fun insertBudget(budget: Budget) = viewModelScope.launch {
        budgetsRepository.insert(budget)
    }

    fun monthBudget(month: Int): Flow<Budget> = flow {
        val budget = budgetsRepository.getByMonth(month)
        emit(budget)
    }

    fun insertPurchasePlan(purchasePlan: PurchasePlanEntity, items: List<PurchasePlanItemEntity>) = viewModelScope.launch {
        purchasePlanRepository.insertPurchasePlan(purchasePlan, items)
    }

    fun getPurchasePlanWithItems(planId: Int): Flow<PurchasePlanWithItems> = flow {
        val purchasePlan = purchasePlanRepository.getPurchasePlanWithItems(planId)
        emit(purchasePlan)
    }

    fun getAllPurchasePlans(): Flow<List<PurchasePlanWithItems>> = flow {
        val purchasePlans = purchasePlanRepository.getAllPurchasePlans()
        emit(purchasePlans)
    }

    fun updatePurchasedStatus(planId: Int, isPurchased: Boolean, purchasePlanItemId: Int) = viewModelScope.launch {
        purchasePlanRepository.updatePurchasedStatus(planId, isPurchased, purchasePlanItemId)
    }

    fun updatePurchasePrice(planId: Int, actualPurchasePrice: Double, purchasePlanItemId: Int) = viewModelScope.launch {
        purchasePlanRepository.updatePurchasePrice(planId, actualPurchasePrice, purchasePlanItemId)
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ExpensesApplication)
                ExpensesViewModel(
                    application.expensesContainer.expensesRepository,
                    application.expensesContainer.budgetsRepository,
                    application.expensesContainer.purchasePlanRepository
                )
            }
        }
    }
}
