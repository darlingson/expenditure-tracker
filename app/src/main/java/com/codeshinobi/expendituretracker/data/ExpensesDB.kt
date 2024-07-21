package com.codeshinobi.expendituretracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codeshinobi.expendituretracker.data.daos.BudgetsDao
import com.codeshinobi.expendituretracker.data.daos.ExpensesDao
import com.codeshinobi.expendituretracker.data.daos.PurchasePlanDao
import com.codeshinobi.expendituretracker.data.entities.Budget
import com.codeshinobi.expendituretracker.data.entities.Expense
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanEntity
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanItemEntity

@Database(entities = [Expense::class, Budget::class, PurchasePlanEntity::class, PurchasePlanItemEntity::class], version = 2)
abstract class ExpensesDB : RoomDatabase() {
    abstract fun expensesDao(): ExpensesDao
    abstract fun budgetsDao(): BudgetsDao
    abstract fun purchasePlanDao(): PurchasePlanDao


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