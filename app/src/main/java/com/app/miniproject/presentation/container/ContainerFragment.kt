package com.app.miniproject.presentation.container

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.miniproject.R
import com.app.miniproject.databinding.FragmentContainerBinding
import com.app.miniproject.presentation.base.BaseVBFragment

class ContainerFragment : BaseVBFragment<FragmentContainerBinding>() {
    override fun getViewBinding(): FragmentContainerBinding =
        FragmentContainerBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentContainer =
            childFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = fragmentContainer.findNavController()
        binding.bottomNav.setupWithNavController(navController)
    }
}