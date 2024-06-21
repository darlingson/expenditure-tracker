package com.codeshinobi.expendituretracker.screens.components

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Parcelize
@Entity(tableName = "expenditures")
data class Expenditure(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "expId")
    val expId: String,
    @ColumnInfo(name = "item")
    val name: String,
    @ColumnInfo(name = "price")
    val amount: Double,
    @ColumnInfo(name = "quantity")
    val quantity: Int,
    @ColumnInfo(name = "date")
    val date: String
) : Parcelable
