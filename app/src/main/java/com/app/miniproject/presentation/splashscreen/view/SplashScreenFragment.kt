package com.app.miniproject.presentation.splashscreen.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.miniproject.databinding.FragmentSplashScreenBinding
import com.app.miniproject.presentation.base.BaseVBFragment
import com.app.miniproject.presentation.splashscreen.viewmodel.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment : BaseVBFragment<FragmentSplashScreenBinding>() {

    private val viewModel by viewModels<SplashScreenViewModel>()

    override fun getViewBinding(): FragmentSplashScreenBinding =
        FragmentSplashScreenBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigateToNextPage()
    }

    private fun navigateToNextPage() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getUserSession
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                    .collect { isLogin ->
                        if (isLogin) {
                            val actionToContainerFragment =
                                SplashScreenFragmentDirections.actionSplashScreenFragmentToContainerFragment()
                            findNavController().navigate(actionToContainerFragment)
                        } else {
                            val actionToLoginFragment =
                                SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment()
                            findNavController().navigate(actionToLoginFragment)
                        }
                    }
            }

        }, DELAY_SPLASH_SCREEN)
    }

    companion object {
        private const val DELAY_SPLASH_SCREEN = 3000L
    }
}