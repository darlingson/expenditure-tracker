package com.codeshinobi.expendituretracker.screens.components

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ExpenditureRepository(private val expenditureDao: ExpenditureDao) {

    val allExpenditures = MutableLiveData<List<Expenditure>>()
    val foundExpenditure = MutableLiveData<Expenditure>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addExpenditure(newExpenditure: Expenditure) {
        coroutineScope.launch(Dispatchers.IO) {
            expenditureDao.addExpenditure(newExpenditure)
        }
    }

    fun updateExpenditureDetails(newExpenditure: Expenditure) {
        coroutineScope.launch(Dispatchers.IO) {
            expenditureDao.updateExpenditureDetails(newExpenditure)
        }
    }
}