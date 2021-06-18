package com.example.rollcall.ui.login

import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.databinding.FragmentLoginBinding
import com.example.rollcall.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }
    val viewMode by viewModels<LoginViewModel>()
    override fun onCreateViews() {
        baseBinding.viewmodel = viewMode
        viewMode.getData()
    }

}