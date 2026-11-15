package com.example.tenantmanagementsystemgroup.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tenants")
data class Tenant(
    val name: String,
    val unitNumber: String = "",
    val phone: String,
    val rent: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) : Serializable {
    fun formattedUnit(): String {
        return if (unitNumber.isNotBlank()) unitNumber else "Unassigned"
    }

    fun formattedRent(): String {
        val amount = rent.toDoubleOrNull()
        return if (amount != null) "KSh %,.0f".format(amount) else "KSh $rent"
    }

    fun summary(): String {
        return "$name (${formattedUnit()})\nPhone: $phone  •  Rent: ${formattedRent()}"
    }
}