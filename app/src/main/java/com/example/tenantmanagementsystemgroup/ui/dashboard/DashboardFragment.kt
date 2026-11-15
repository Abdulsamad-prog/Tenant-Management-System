package com.example.tenantmanagementsystemgroup.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tenantmanagementsystemgroup.data.local.SessionManager
import com.example.tenantmanagementsystemgroup.databinding.FragmentDashboardBinding
import com.example.tenantmanagementsystemgroup.ui.viewmodel.TenantViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TenantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val landlordName = SessionManager(requireContext()).getFullName()
        binding.welcomeTextView.text = "Welcome back, $landlordName"

        viewModel.tenantCount.observe(viewLifecycleOwner) { count ->
            binding.tenantCountTextView.text = "Tenants\n$count"
        }

        viewModel.totalRent.observe(viewLifecycleOwner) { totalRent ->
            binding.totalRentTextView.text = "Total rent\nKSh %,.0f".format(totalRent)
        }

        binding.addTenantButton.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToAddTenantFragment()
            findNavController().navigate(action)
        }

        binding.viewTenantsButton.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToViewTenantsFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}