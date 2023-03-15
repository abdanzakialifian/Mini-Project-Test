package com.app.miniproject.presentation.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.fragment.findNavController
import com.app.miniproject.databinding.FragmentSplashScreenBinding
import com.app.miniproject.presentation.base.BaseVBFragment

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : BaseVBFragment<FragmentSplashScreenBinding>() {
    override fun getViewBinding(): FragmentSplashScreenBinding =
        FragmentSplashScreenBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNextPage()
        }, DELAY_SPLASH_SCREEN)
    }

    private fun navigateToNextPage() {
        val actionToLoginFragment =
            SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment()
        findNavController().navigate(actionToLoginFragment)
    }

    companion object {
        private const val DELAY_SPLASH_SCREEN = 3000L
    }
}