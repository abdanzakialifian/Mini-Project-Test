package com.app.miniproject.presentation.home.view

import android.os.Bundle
import android.view.View
import com.app.miniproject.databinding.FragmentHomeBinding
import com.app.miniproject.presentation.base.BaseVBFragment

class HomeFragment : BaseVBFragment<FragmentHomeBinding>() {
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}