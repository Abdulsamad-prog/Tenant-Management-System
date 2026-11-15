package com.example.tenantmanagementsystemgroup.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tenantmanagementsystemgroup.data.model.Tenant

@Dao
interface TenantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTenant(tenant: Tenant)

    @Delete
    suspend fun deleteTenant(tenant: Tenant)

    @Query("SELECT * FROM tenants ORDER BY id DESC")
    fun getAllTenants(): LiveData<List<Tenant>>

    @Query("SELECT COUNT(*) FROM tenants")
    fun getTenantCount(): LiveData<Int>
}