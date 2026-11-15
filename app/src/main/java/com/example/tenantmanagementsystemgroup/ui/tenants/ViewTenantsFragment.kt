package com.example.tenantmanagementsystemgroup.ui.tenants

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tenantmanagementsystemgroup.data.model.Tenant
import com.example.tenantmanagementsystemgroup.databinding.FragmentViewTenantsBinding
import com.example.tenantmanagementsystemgroup.ui.viewmodel.TenantViewModel

class ViewTenantsFragment : Fragment() {

    private var _binding: FragmentViewTenantsBinding? = null
    private val binding get() = _binding!!
    private val args: ViewTenantsFragmentArgs by navArgs()
    private val viewModel: TenantViewModel by activityViewModels()
    private lateinit var tenantAdapter: TenantAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewTenantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newTenant = args.newTenant
        binding.tenant = newTenant

        if (newTenant == null) {
            binding.newTenantGroup.visibility = View.GONE
        } else {
            binding.callButton.setOnClickListener {
                dialTenant(newTenant.phone)
            }
        }

        tenantAdapter = TenantAdapter(
            onCallClick = { tenant -> dialTenant(tenant.phone) },
            onDeleteClick = { tenant -> confirmDeleteTenant(tenant) }
        )

        binding.tenantsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tenantAdapter
        }

        viewModel.tenants.observe(viewLifecycleOwner) { tenants ->
            tenantAdapter.submitList(tenants)
            binding.emptyStateCard.visibility = if (tenants.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun dialTenant(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        startActivity(intent)
    }

    private fun confirmDeleteTenant(tenant: Tenant) {
        AlertDialog.Builder(requireContext())
            .setTitle("Remove Tenant")
            .setMessage("Remove ${tenant.name} (${tenant.formattedUnit()}) from your property records?")
            .setPositiveButton("Remove") { _, _ ->
                viewModel.deleteTenant(tenant)
                if (args.newTenant?.id == tenant.id || args.newTenant?.name == tenant.name) {
                    binding.newTenantGroup.visibility = View.GONE
                }
                Toast.makeText(requireContext(), "${tenant.name} removed", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}