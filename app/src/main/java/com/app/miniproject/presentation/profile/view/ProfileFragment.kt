package com.app.miniproject.presentation.profile.view

import com.app.miniproject.databinding.FragmentProfileBinding
import com.app.miniproject.presentation.base.BaseVBFragment

class ProfileFragment : BaseVBFragment<FragmentProfileBinding>() {
    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)
}