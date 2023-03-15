package com.app.miniproject.presentation.login.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.miniproject.R
import com.app.miniproject.databinding.FragmentLoginBinding
import com.app.miniproject.presentation.base.BaseVBFragment
import com.app.miniproject.presentation.login.viewmodel.LoginViewModel
import com.app.miniproject.utils.UiState
import com.app.miniproject.utils.gone
import com.app.miniproject.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


@AndroidEntryPoint
class LoginFragment : BaseVBFragment<FragmentLoginBinding>() {

    private val viewModel by viewModels<LoginViewModel>()

    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleTextInputPassword()
        binding.apply {
            btnSignIn.setOnClickListener {
                callApi()
            }
            tvSignUp.setOnClickListener {
                val navigateToRegistrationFragment =
                    LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
                findNavController().navigate(navigateToRegistrationFragment)
            }
        }
    }

    private fun callApi() {
        val jsonObject = JSONObject()
        jsonObject.put("username", binding.edtUsername.text.toString())
        jsonObject.put("password", binding.edtPassword.text.toString())

        val jsonObjectToString = jsonObject.toString()

        val requestBody = jsonObjectToString.toRequestBody("application/json".toMediaTypeOrNull())

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.postLogin(requestBody)
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> Toast.makeText(
                            requireContext(), "Loading", Toast.LENGTH_SHORT
                        ).show()
                        is UiState.Success -> {
                            viewModel.saveUserSession(true)
                            viewModel.saveUserToken(uiState.data.data?.token ?: "")
                            val actionToContainerFragment =
                                LoginFragmentDirections.actionLoginFragmentToContainerFragment()
                            findNavController().navigate(actionToContainerFragment)
                        }
                        is UiState.Error -> Toast.makeText(
                            requireContext(), "ERROR", Toast.LENGTH_SHORT
                        ).show()
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