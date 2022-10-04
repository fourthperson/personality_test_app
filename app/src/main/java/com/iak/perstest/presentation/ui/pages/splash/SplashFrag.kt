package com.iak.perstest.presentation.ui.pages.splash

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iak.perstest.databinding.FragSplashBinding

class SplashFrag : Fragment() {
    private var _binding: FragSplashBinding? = null
    private val layout get() = _binding!!

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragSplashBinding.inflate(inflater, container, false)
        return layout.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        viewModel.setNavController(findNavController())
        animate()
    }

    private fun animate() {
        val listener = object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
            }

            override fun onAnimationEnd(p0: Animator) {
                viewModel.landingPage()
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {
            }
        }
        layout.animationView.addAnimatorListener(listener)
        layout.animationView.playAnimation()
    }
}