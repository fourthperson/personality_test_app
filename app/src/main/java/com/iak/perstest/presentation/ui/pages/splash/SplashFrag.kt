package com.iak.perstest.presentation.ui.pages.splash

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iak.perstest.R
import com.iak.perstest.databinding.FragSplashBinding

class SplashFrag : Fragment() {
    private var _binding: FragSplashBinding? = null
    private val layout get() = _binding!!

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
        animate()
    }

    private fun animate() {
        val listener = object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {

            }

            override fun onAnimationEnd(p0: Animator) {
                findNavController().popBackStack(R.id.splashFrag, true)
                findNavController().navigate(R.id.actionLanding)
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