package com.example.a12_fragmentnavigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a12_fragmentnavigation.BuildConfig
import com.example.a12_fragmentnavigation.R
import com.example.a12_fragmentnavigation.contract.HasCustomTitle
import com.example.a12_fragmentnavigation.contract.navigator
import com.example.a12_fragmentnavigation.databinding.FragmentAboutBinding

class AboutFragment : Fragment(), HasCustomTitle {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAboutBinding.inflate(inflater, container, false).apply {
        versionNameTextView.text = BuildConfig.VERSION_NAME
        versionCodeTextView.text = BuildConfig.VERSION_CODE.toString()
        okButton.setOnClickListener { onOkPressed() }
    }.root

    override fun getTitleRes(): Int = R.string.about
    private fun onOkPressed() = navigator().goBack()

    companion object {
        @JvmStatic
        fun newInstance() = AboutFragment()
    }
}
