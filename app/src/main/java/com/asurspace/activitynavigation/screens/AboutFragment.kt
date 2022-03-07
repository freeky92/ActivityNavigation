package com.asurspace.activitynavigation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asurspace.activitynavigation.R
import com.asurspace.activitynavigation.contract.HasCustomTitle
import com.asurspace.activitynavigation.contract.navigator
import com.asurspace.activitynavigation.databinding.FragmentAboutBinding

class AboutFragment : Fragment(), HasCustomTitle {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        with(binding) {
            versionNameField.text = requireContext().packageManager.getPackageInfo(
                requireContext().packageName,
                0
            ).versionName

            versionCodeField.text = requireContext().packageManager.getPackageInfo(
                requireContext().packageName,
                0
            ).versionCode.toString()
            okTb.setOnClickListener { navigator().goBack() }
        }

        return binding.root
    }

    override fun getTitleRes() = R.string.about_bt

}