package com.example.tenantmanagementsystemgroup.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tenantmanagementsystemgroup.data.model.Tenant
import com.example.tenantmanagementsystemgroup.data.repository.TenantRepository

class TenantViewModel : ViewModel() {

    private val _tenants = MutableLiveData<List<Tenant>>(TenantRepository.tenants.toList())
    val tenants: LiveData<List<Tenant>> = _tenants

    private val _tenantCount = MutableLiveData<Int>(TenantRepository.tenants.size)
    val tenantCount: LiveData<Int> = _tenantCount

    private val _totalRent = MutableLiveData<Double>(TenantRepository.totalRent())
    val totalRent: LiveData<Double> = _totalRent

    fun addTenant(tenant: Tenant) {
        TenantRepository.addTenant(tenant)
        _tenants.value = TenantRepository.tenants.toList()
        _tenantCount.value = TenantRepository.tenants.size
        _totalRent.value = TenantRepository.totalRent()
    }
}