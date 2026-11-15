package com.example.tenantmanagementsystemgroup.ui.viewmodel

import android.app.Application
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

    init {
        val tenantDao = TenantDatabase.getDatabase(application).tenantDao()
        repository = TenantRepository(tenantDao)
        tenants = repository.allTenants
        tenantCount = repository.tenantCount
        totalRent = repository.allTenants.map { list ->
            repository.totalRent(list)
        }
    }

    fun addTenant(tenant: Tenant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTenant(tenant)
        }
    }

    fun deleteTenant(tenant: Tenant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTenant(tenant)
        }
    }
}