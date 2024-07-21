package com.codeshinobi.expendituretracker.data.repositories

import com.codeshinobi.expendituretracker.data.daos.PurchasePlanDao
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanEntity
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanItemEntity
import com.codeshinobi.expendituretracker.data.entities.PurchasePlanWithItems

class PurchasePlanRepository(private val purchasePlanDao: PurchasePlanDao) {

    suspend fun insertPurchasePlan(purchasePlan: PurchasePlanEntity, items: List<PurchasePlanItemEntity>) {
        val planId = purchasePlanDao.insertPurchasePlan(purchasePlan)
        items.forEach { it.purchasePlanId = planId.toInt() }
        purchasePlanDao.insertPurchasePlanItems(items)
    }

    suspend fun getPurchasePlanWithItems(planId: Int): PurchasePlanWithItems {
        return purchasePlanDao.getPurchasePlanWithItems(planId)
    }

    suspend fun getAllPurchasePlans(): List<PurchasePlanWithItems> {
        return purchasePlanDao.getAllPurchasePlans()
    }

    suspend fun updatePurchasedStatus(planId: Int, isPurchased: Boolean, purchasePlanItemId: Int) {
        purchasePlanDao.updatePurchasedStatus(planId, isPurchased, purchasePlanItemId)
    }

    suspend fun updatePurchasePrice(planId: Int, actualPurchasePrice: Double, purchasePlanItemId: Int) {
        purchasePlanDao.updatePurchasePrice(planId, actualPurchasePrice, purchasePlanItemId)
    }
}