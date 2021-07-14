package com.example.rollcall.ui.login

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.databinding.FragmentLoginBinding
import com.example.rollcall.ui.admin.home.HomeAdminFragment
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils.TOKEN
import com.example.rollcall.utils.Utils.gotoFragment
import com.royrodriguez.transitionbutton.TransitionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }


    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<LoginViewModel>()


    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        setup()
        clickView()
    }

    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        baseBinding.apply {
            viewmodel = viewModel
        }

        viewModel.user.observe(viewLifecycleOwner,{
            it?.let {
                if (it.message == null) {
                    baseBinding.btnLogin.stopAnimation(
                        TransitionButton.StopAnimationStyle.EXPAND
                    ) {
                        val fragment = HomeAdminFragment()
                        fragment.arguments =
                            Bundle().apply { putString(TOKEN, it.data?.get(0)?.token) }
                        gotoFragment(requireActivity(), fragment)
                    }
                } else {
                    baseBinding.btnLogin.stopAnimation(
                        TransitionButton.StopAnimationStyle.SHAKE,
                        null
                    )
                }
            }
        })

    }

    private fun clickView() {
        baseBinding.btnLogin.apply {
            setOnClickListener {
                startAnimation()
                viewModel.login()
            }
        }
    }

}