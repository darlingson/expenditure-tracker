package com.codeshinobi.expendituretracker.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.codeshinobi.expendituretracker.data.entities.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensesDao {
    @Query("SELECT * FROM expenses")
    fun getAll(): Flow<List<Expense>>
    @Query("SELECT * FROM expenses ORDER BY date LIMIT 10")
    fun getLatest(): Flow<List<Expense>>

    @Query("SELECT * FROM expenses WHERE id = :id")
    fun getById(id: Int): Expense

    @Insert
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)
    @Delete
    suspend fun deleteAll(expenses: List<Expense>)
}