package com.app.miniproject.presentation.profile.view

import android.graphics.Color
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
            requireContext().showAlertDialogInformation(layoutInflater, title, subTitle) { alertDialog, _ ->
                val actionToLoginFragment =
                    ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
                findNavController().navigate(actionToLoginFragment)
                viewModel.deleteLocalData()
                alertDialog.dismiss()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getUserName
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { name ->
                    binding.tvName.text = name
                    setInitialImage(name)
                }
        }
    }

    private fun setInitialImage(name: String) {
        val splitName = name.split(" ")
        val firstInitial = splitName.firstOrNull()
        val lastInitial = splitName.lastOrNull()
        val firstCharacter = firstInitial?.take(1)
        val lastCharacter = lastInitial?.take(1)
        binding.tvInitialName.text =
            StringBuilder().append(firstCharacter).append(lastCharacter)
        val colors = arrayOf(
            Color.parseColor("#C0392B"),
            Color.parseColor("#2980B9"),
            Color.parseColor("#1ABC9C"),
            Color.parseColor("#F1C40F"),
            Color.parseColor("#95A5A6"),
            Color.parseColor("#34495E")
        )
        val randomColor = colors.random()

        binding.tvInitialName.background.setTint(randomColor)
    }
}