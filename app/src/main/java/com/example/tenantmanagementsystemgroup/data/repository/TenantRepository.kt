package com.example.tenantmanagementsystemgroup.data.repository

import androidx.lifecycle.LiveData
import com.example.tenantmanagementsystemgroup.data.local.TenantDao
import com.example.tenantmanagementsystemgroup.data.model.Tenant

class TenantRepository(private val tenantDao: TenantDao) {

    val allTenants: LiveData<List<Tenant>> = tenantDao.getAllTenants()
    val tenantCount: LiveData<Int> = tenantDao.getTenantCount()

    suspend fun addTenant(tenant: Tenant) {
        tenantDao.insertTenant(tenant)
    }

    suspend fun deleteTenant(tenant: Tenant) {
        tenantDao.deleteTenant(tenant)
    }

    fun totalRent(tenants: List<Tenant>): Double {
        return tenants.sumOf { it.rent.toDoubleOrNull() ?: 0.0 }
    }
}