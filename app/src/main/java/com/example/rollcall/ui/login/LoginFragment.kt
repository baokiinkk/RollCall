package com.example.rollcall.ui.login

import android.os.Handler
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.databinding.FragmentLoginBinding
import com.example.rollcall.ui.admin.home.HomeAdminFragment
import com.example.rollcall.utils.BaseFragment
import com.royrodriguez.transitionbutton.TransitionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    val viewMode by viewModels<LoginViewModel>()
    override fun onCreateViews() {
        baseBinding.apply {
            viewmodel = viewMode
            btnLogin.setOnClickListener {
                btnLogin.startAnimation()
                val handler = Handler()
                handler.postDelayed(Runnable {
                    val isSuccessful = true

                    // Choose a stop animation if your call was succesful or not
                    if (isSuccessful) {
                        btnLogin.stopAnimation(
                            TransitionButton.StopAnimationStyle.EXPAND
                        ) {
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.container, HomeAdminFragment())
                                .commit()
                        }
                    } else {
                        btnLogin.stopAnimation(
                            TransitionButton.StopAnimationStyle.SHAKE,
                            null
                        )
                    }
                }, 2000)
            }
        }
    }

}