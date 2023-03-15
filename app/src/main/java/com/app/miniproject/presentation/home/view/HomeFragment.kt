package com.app.miniproject.presentation.home.view

import com.app.miniproject.databinding.FragmentHomeBinding
import com.app.miniproject.presentation.base.BaseVBFragment

class HomeFragment : BaseVBFragment<FragmentHomeBinding>() {
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)
}