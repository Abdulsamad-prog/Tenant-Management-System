package com.example.tenantmanagementsystemgroup.ui.tenants

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.tenantmanagementsystemgroup.databinding.FragmentViewTenantsBinding
import com.example.tenantmanagementsystemgroup.ui.viewmodel.TenantViewModel

class ViewTenantsFragment : Fragment() {

    private var _binding: FragmentViewTenantsBinding? = null
    private val binding get() = _binding!!
    private val args: ViewTenantsFragmentArgs by navArgs()
    private val viewModel: TenantViewModel by activityViewModels()

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
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${newTenant.phone}"))
                startActivity(intent)
            }
        }

        viewModel.tenants.observe(viewLifecycleOwner) { tenants ->
            if (tenants.isEmpty()) {
                binding.allTenantsTextView.text = "No tenants yet. Add one from the Dashboard."
            } else {
                binding.allTenantsTextView.text = tenants.joinToString("\n\n") { it.summary() }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}