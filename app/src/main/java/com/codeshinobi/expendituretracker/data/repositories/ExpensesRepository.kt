package com.codeshinobi.expendituretracker.data.repositories

import com.codeshinobi.expendituretracker.data.daos.ExpensesDao
import com.codeshinobi.expendituretracker.data.entities.Expense

class ExpensesRepository(private val expensesDao: ExpensesDao) {
    fun getAll() = expensesDao.getAll()
    suspend fun insert(expense: Expense) = expensesDao.insert(expense)
    suspend fun delete(expense: Expense) = expensesDao.delete(expense)
    suspend fun deleteAll( expenses: List<Expense>) = expensesDao.deleteAll(expenses)
}

