package com.codeshinobi.expendituretracker.screens.components

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExpenditureDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addExpenditure(Expenditure: Expenditure)

    @Query("SELECT * FROM expenditures WHERE expId = :expId")
    fun findExpenditureById(expId: String): Expenditure

    @Query("SELECT * FROM expenditures")
    fun getAllExpenditures(): List<Expenditure>

    @Update
    suspend fun updateExpenditureDetails(expenditure: Expenditure)

    @Delete
    suspend fun deleteExpenditure(expenditure: Expenditure)

}