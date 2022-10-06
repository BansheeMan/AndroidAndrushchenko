package com.example.a12_fragmentnavigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a12_fragmentnavigation.R
import com.example.a12_fragmentnavigation.contract.HasCustomTitle
import com.example.a12_fragmentnavigation.contract.navigator
import com.example.a12_fragmentnavigation.databinding.FragmentBoxBinding

class BoxFragment : Fragment(), HasCustomTitle {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentBoxBinding.inflate(inflater, container, false).apply {
        toMainMenuButton.setOnClickListener { onToMainMenuPressed() }
    }.root

    private fun onToMainMenuPressed() {
        navigator().goToMenu()
    }

    override fun getTitleRes(): Int = R.string.box

    companion object {
        @JvmStatic
        fun newInstance() = BoxFragment()
    }

}
