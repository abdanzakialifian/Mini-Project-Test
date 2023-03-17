package com.app.miniproject.presentation.container

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.miniproject.R
import com.app.miniproject.databinding.ActivityContainerBinding
import com.app.miniproject.utils.gone
import com.app.miniproject.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContainerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set bottom navigation with navigation component
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.label == "fragment_splash_screen" || destination.label == "fragment_login" || destination.label == "fragment_registration") {
                binding.bottomNav.gone()
            } else {
                binding.bottomNav.visible()
            }
        }
        binding.bottomNav.setupWithNavController(navController)
    }
}