package com.asurspace.activitynavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
            versionNameField.text = BuildConfig.VERSION_NAME
            versionCodeField.text = BuildConfig.VERSION_CODE.toString()
            okTb.setOnClickListener { navigator().goBack() }
        }

        return binding.root
    }

    override fun getTitleRes() = R.string.about_bt

}