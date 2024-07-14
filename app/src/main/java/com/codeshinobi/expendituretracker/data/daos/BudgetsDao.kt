package com.codeshinobi.expendituretracker.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.codeshinobi.expendituretracker.data.entities.Budget
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetsDao {
    @Insert
    suspend fun insert(budget: Budget)

    @Delete
    suspend fun delete(budget: Budget)
    @Delete
    fun deleteAll(budgets: List<Budget>)
    @Update
    suspend fun update(budget: Budget)

    @Query("SELECT * FROM budgets")
    fun getAll(): Flow<List<Budget>>

    @Query("SELECT * FROM budgets WHERE id = :id")
    fun getById(id: Int): Budget

    @Query("SELECT * FROM budgets WHERE month = :month")
    suspend fun getByMonth(month: Int): Budget

}