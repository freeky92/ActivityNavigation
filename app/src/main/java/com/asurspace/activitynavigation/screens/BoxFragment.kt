package com.asurspace.activitynavigation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asurspace.activitynavigation.R
import com.asurspace.activitynavigation.contract.HasCustomTitle
import com.asurspace.activitynavigation.contract.navigator
import com.asurspace.activitynavigation.databinding.FragmentBoxBinding

class BoxFragment : Fragment(), HasCustomTitle {

    private var _binding: FragmentBoxBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoxBinding.inflate(inflater, container, false)

        binding.toMainTb.setOnClickListener {
            onToMainMenuPressed()
        }

        return binding.root
    }

    private fun onToMainMenuPressed() {
        navigator().goToMenu()
    }

    override fun getTitleRes() = R.string.box

}