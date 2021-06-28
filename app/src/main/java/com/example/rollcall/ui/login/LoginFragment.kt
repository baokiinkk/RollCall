package com.example.rollcall.ui.login

import android.os.Handler
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
                viewMode.login()
            }
        }
        viewMode.user.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.message == null) {
                    baseBinding.btnLogin.stopAnimation(
                        TransitionButton.StopAnimationStyle.EXPAND
                    ) {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.container, HomeAdminFragment())
                            .commit()
                    }
                } else {
                    baseBinding.btnLogin.stopAnimation(
                        TransitionButton.StopAnimationStyle.SHAKE,
                        null
                    )
                }
                Log.d("quocbao", it.toString())
            }
        })
    }

}