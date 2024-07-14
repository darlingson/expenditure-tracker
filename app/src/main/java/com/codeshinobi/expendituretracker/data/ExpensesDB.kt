package com.codeshinobi.expendituretracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codeshinobi.expendituretracker.data.daos.BudgetsDao
import com.codeshinobi.expendituretracker.data.daos.ExpensesDao
import com.codeshinobi.expendituretracker.data.entities.Budget
import com.codeshinobi.expendituretracker.data.entities.Expense

@Database(entities = [Expense::class, Budget::class], version = 1)
abstract class ExpensesDB : RoomDatabase() {
    abstract fun expensesDao(): ExpensesDao
    abstract fun budgetsDao(): BudgetsDao


    companion object {
        @Volatile
        private var Instance: ExpensesDB? = null

        fun getExpensesDatabase(context: Context): ExpensesDB {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = ExpensesDB::class.java,
                    name = "sample"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}