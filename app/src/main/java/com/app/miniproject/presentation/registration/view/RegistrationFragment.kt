package com.app.miniproject.presentation.registration.view

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.miniproject.R
import com.app.miniproject.databinding.FragmentRegistrationBinding
import com.app.miniproject.presentation.base.BaseVBFragment
import com.app.miniproject.presentation.registration.viewmodel.RegistrationViewModel
import com.app.miniproject.utils.UiState
import com.app.miniproject.utils.gone
import com.app.miniproject.utils.showSnackBar
import com.app.miniproject.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

@AndroidEntryPoint
class RegistrationFragment : BaseVBFragment<FragmentRegistrationBinding>() {

    private val viewModel by viewModels<RegistrationViewModel>()

    override fun getViewBinding(): FragmentRegistrationBinding =
        FragmentRegistrationBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleTextInputPassword()
        binding.apply {
            btnSignUp.setOnClickListener {
                callApi()
            }
            imgBack.setOnClickListener {
                findNavController().navigateUp()
            }
            tvSignIn.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun callApi() {
        val jsonObject = JSONObject()
        jsonObject.put("profileName", binding.edtName.text.toString())
        jsonObject.put("username", binding.edtUsername.text.toString())
        jsonObject.put("password", binding.edtPassword.text.toString())

        val jsonObjectToString = jsonObject.toString()

        val requestBody = jsonObjectToString.toRequestBody("application/json".toMediaTypeOrNull())

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.postRegister(requestBody)
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> binding.progressBar.visible()
                        is UiState.Success -> {
                            binding.progressBar.gone()
                            view?.showSnackBar(layoutInflater, uiState.data.message.toString())
                        }
                        is UiState.Error -> {
                            binding.progressBar.gone()
                            view?.showSnackBar(layoutInflater, uiState.message)
                        }
                    }
                }
        }
    }

    private fun handleTextInputPassword() {
        binding.apply {
            edtPassword.doAfterTextChanged {
                if (it?.isNotEmpty() == true && it.length < 8) {
                    hintErrorPassword.visible()
                    inputLayoutPassword.boxStrokeColor =
                        ContextCompat.getColor(requireContext(), R.color.red)
                } else {
                    hintErrorPassword.gone()
                    inputLayoutPassword.isErrorEnabled = false
                    inputLayoutPassword.boxStrokeColor =
                        ContextCompat.getColor(requireContext(), R.color.green)
                }
            }
        }
    }
}