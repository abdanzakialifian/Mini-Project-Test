package com.app.miniproject.presentation.profile.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.miniproject.R
import com.app.miniproject.databinding.FragmentProfileBinding
import com.app.miniproject.presentation.base.BaseVBFragment
import com.app.miniproject.presentation.container.ContainerFragmentDirections
import com.app.miniproject.presentation.profile.viewmodel.ProfileViewModel
import com.app.miniproject.utils.showAlertDialogInformation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseVBFragment<FragmentProfileBinding>() {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            val title = resources.getString(R.string.title_alert_dialog_information)
            val subTitle = resources.getString(R.string.make_sure_logout)
            requireContext().showAlertDialogInformation(layoutInflater, title, subTitle) { _, _ ->
                val actionToLoginFragment =
                    ContainerFragmentDirections.actionContainerFragmentToLoginFragment()
                findNavController().navigate(actionToLoginFragment)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getUserName
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { name ->
                    binding.tvName.text = name
                }
        }
    }
}