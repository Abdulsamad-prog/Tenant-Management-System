package com.example.tenantmanagementsystemgroup.ui.tenants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tenantmanagementsystemgroup.data.model.Tenant
import com.example.tenantmanagementsystemgroup.databinding.FragmentAddTenantBinding
import com.example.tenantmanagementsystemgroup.ui.viewmodel.TenantViewModel

class AddTenantFragment : Fragment() {

    private var _binding: FragmentAddTenantBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TenantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTenantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            val name = binding.tenantNameEditText.text.toString().trim()
            val unitNumber = binding.unitNumberEditText.text.toString().trim()
            val phone = binding.phoneEditText.text.toString().trim()
            val rent = binding.rentEditText.text.toString().trim()

            if (name.isEmpty() || unitNumber.isEmpty() || phone.isEmpty() || rent.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newTenant = Tenant(
                name = name,
                unitNumber = unitNumber,
                phone = phone,
                rent = rent
            )
            viewModel.addTenant(newTenant)

            Toast.makeText(requireContext(), "Tenant saved!", Toast.LENGTH_SHORT).show()
            val action = AddTenantFragmentDirections.actionAddTenantFragmentToViewTenantsFragment(newTenant)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}