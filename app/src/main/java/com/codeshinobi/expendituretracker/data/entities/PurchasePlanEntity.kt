package com.codeshinobi.expendituretracker.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "purchase_plans")
data class PurchasePlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val totalPurchasePlanAmount: Double
)
@Entity(
    tableName = "purchase_plan_items",
    foreignKeys = [ForeignKey(
        entity = PurchasePlanEntity::class,
        parentColumns = ["id"],
        childColumns = ["purchasePlanId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class PurchasePlanItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var purchasePlanId: Int,
    val itemName: String,
    val quantity: Int,
    val estimatedPricePerItem: Double = 0.0,
    val estimatedItemPriceTotal: Double = 0.0,
    val actualPurchasePrice: Double = 0.0,
    val isPurchased: Boolean = false
)
data class PurchasePlanWithItems(
    @Embedded val purchasePlan: PurchasePlanEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "purchasePlanId"
    )
    val items: List<PurchasePlanItemEntity>
)