package com.example.tenantmanagementsystemgroup.ui.tenants

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tenantmanagementsystemgroup.data.model.Tenant
import com.example.tenantmanagementsystemgroup.databinding.ItemTenantBinding

class TenantAdapter(
    private val onCallClick: (Tenant) -> Unit,
    private val onDeleteClick: (Tenant) -> Unit
) : ListAdapter<Tenant, TenantAdapter.TenantViewHolder>(TenantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantViewHolder {
        val binding = ItemTenantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TenantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TenantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TenantViewHolder(
        private val binding: ItemTenantBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tenant: Tenant) {
            binding.tenantNameTextView.text = tenant.name
            binding.unitBadgeTextView.text = tenant.formattedUnit()
            binding.tenantDetailsTextView.text = "${tenant.phone}  •  ${tenant.formattedRent()}"

            val initials = tenant.name
                .split(" ")
                .filter { it.isNotBlank() }
                .take(2)
                .joinToString("") { it.first().uppercase() }
                .ifEmpty { "T" }
            binding.initialsTextView.text = initials

            binding.cardCallButton.setOnClickListener { onCallClick(tenant) }
            binding.cardDeleteButton.setOnClickListener { onDeleteClick(tenant) }
        }
    }

    class TenantDiffCallback : DiffUtil.ItemCallback<Tenant>() {
        override fun areItemsTheSame(oldItem: Tenant, newItem: Tenant): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Tenant, newItem: Tenant): Boolean = oldItem == newItem
    }
}