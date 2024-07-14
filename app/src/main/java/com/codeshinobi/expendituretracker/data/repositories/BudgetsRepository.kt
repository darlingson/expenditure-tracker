package com.codeshinobi.expendituretracker.data.repositories

import com.codeshinobi.expendituretracker.data.daos.BudgetsDao
import com.codeshinobi.expendituretracker.data.entities.Budget

class BudgetsRepository(private val budgetsDao: BudgetsDao) {
    fun getAll() = budgetsDao.getAll()
    suspend fun insert(budget: Budget) = budgetsDao.insert(budget)
    suspend fun delete(budget: Budget) = budgetsDao.delete(budget)
    suspend fun deleteAll( budgets: List<Budget>) = budgetsDao.deleteAll(budgets)
    suspend fun getByMonth(month: Int) = budgetsDao.getByMonth(month)
}