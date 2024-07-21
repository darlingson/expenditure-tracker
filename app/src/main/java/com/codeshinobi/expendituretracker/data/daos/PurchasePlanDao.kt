package com.codeshinobi.expendituretracker.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanEntity
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanItemEntity
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanWithItems

@Dao
interface PurchasePlanDao {
    @Insert
    suspend fun insertPurchasePlan(purchasePlan: PurchasePlanEntity): Long

    @Insert
    suspend fun insertPurchasePlanItems(items: List<PurchasePlanItemEntity>)

    @Transaction
    @Query("SELECT * FROM purchase_plans WHERE id = :planId")
    suspend fun getPurchasePlanWithItems(planId: Int): PurchasePlanWithItems

    @Query("SELECT * FROM purchase_plans")
    suspend fun getAllPurchasePlans(): List<PurchasePlanWithItems>

    @Query("UPDATE purchase_plan_items SET isPurchased = :isPurchased WHERE purchasePlanId = :planId AND id = :purchasePlanItemId")
    suspend fun updatePurchasedStatus(planId: Int, isPurchased: Boolean, purchasePlanItemId: Int)

    @Query("UPDATE purchase_plan_items SET actualPurchasePrice = :actualPurchasePrice WHERE purchasePlanId = :planId AND id = :purchasePlanItemId")
    suspend fun updatePurchasePrice(planId: Int, actualPurchasePrice: Double, purchasePlanItemId: Int)
}