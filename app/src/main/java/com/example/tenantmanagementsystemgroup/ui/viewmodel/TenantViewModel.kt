
package com.example.tenantmanagementsystemgroup.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.tenantmanagementsystemgroup.data.local.TenantDatabase
import com.example.tenantmanagementsystemgroup.data.model.Tenant
import com.example.tenantmanagementsystemgroup.data.repository.TenantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TenantViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TenantRepository

    val tenants: LiveData<List<Tenant>>
    val tenantCount: LiveData<Int>
    val totalRent: LiveData<Double>

    // Stage 2: Formatted dashboard summaries
    val tenantCountText: LiveData<String>
    val totalRentText: LiveData<String>
    val allTenantsText: LiveData<String>

    init {
        val tenantDao = TenantDatabase.getDatabase(application).tenantDao()
        repository = TenantRepository(tenantDao)

        tenants = repository.allTenants
        tenantCount = repository.tenantCount

        totalRent = tenants.map { list ->
            repository.totalRent(list)
        }

        tenantCountText = tenants.map { list ->
            "Tenants\n${list.size}"
        }

        totalRentText = totalRent.map { total ->
            "Total rent\nKSh %,.0f".format(total)
        }

        allTenantsText = tenants.map { list ->
            if (list.isEmpty()) {
                "No tenants yet. Add one from the Dashboard."
            } else {
                list.joinToString("\n\n") { tenant ->
                    tenant.summary()
                }
            }
        }

        Log.d("TENANT_LIFECYCLE", "TenantViewModel: created")
    }

    // Add a tenant using the existing Tenant model
    fun addTenant(tenant: Tenant): Boolean {

        val cleanName = tenant.name.trim()
        val cleanUnitNumber = tenant.unitNumber.trim()
        val cleanPhone = tenant.phone.trim()
        val cleanRent = tenant.rent.trim()

        // Validate required fields
        if (cleanName.isBlank() ||
            cleanPhone.isBlank() ||
            cleanRent.isBlank()
        ) {
            return false
        }

        val cleanTenant = tenant.copy(
            name = cleanName,
            unitNumber = cleanUnitNumber,
            phone = cleanPhone,
            rent = cleanRent
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.addTenant(cleanTenant)
        }

        return true
    }

    // Stage 2: Add a tenant using individual input values
    fun addTenant(
        name: String,
        phone: String,
        rent: String
    ): Tenant? {

        val cleanName = name.trim()
        val cleanPhone = phone.trim()
        val cleanRent = rent.trim()

        if (cleanName.isBlank() ||
            cleanPhone.isBlank() ||
            cleanRent.isBlank()
        ) {
            return null
        }

        val tenant = Tenant(
            name = cleanName,
            phone = cleanPhone,
            rent = cleanRent
        )

        return if (addTenant(tenant)) tenant else null
    }

    // Delete a tenant
    fun deleteTenant(tenant: Tenant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTenant(tenant)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TENANT_LIFECYCLE", "TenantViewModel: onCleared")
    }
}
