package com.example.rollcall.ui.admin.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.databinding.FragmentHomeAdminBinding
import com.example.rollcall.ui.admin.classes.ClassFragment
import com.example.rollcall.ui.admin.user.UserFragment
import com.example.rollcall.ui.login.LoginFragment
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.example.rollcall.utils.Utils.CLASS
import com.example.rollcall.utils.Utils.STUDENT
import com.example.rollcall.utils.Utils.TEACHER
import com.example.rollcall.utils.Utils.TOKEN
import com.example.rollcall.utils.Utils.USER
import com.example.rollcall.utils.Utils.gotoFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeAdminFragment : BaseFragment<FragmentHomeAdminBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_home_admin
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<HomeAdminViewModel>()
    private var token: String? = null


    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        getArgument()
        getData()
        setup()
        clickView()
    }


    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        baseBinding.apply {
            viewmodel = viewModel
        }
        viewModel.isLogOut.observe(viewLifecycleOwner,{
            it?.let {
                gotoFragment(requireActivity(), LoginFragment())
            }
        })
    }

    private fun getData() {
        token?.let { viewModel.getData(it) }
    }

    private fun getArgument() {
        token = arguments?.getString(TOKEN)
    }

    private fun clickView() {
        baseBinding.apply {
            cardViewStudent.setOnClickListener {
                val fragment = UserFragment()
                fragment.arguments = Bundle().apply {
                    putString(TOKEN, token)
                    putString(USER, STUDENT)
                }
                gotoFragment(requireActivity(), fragment)
            }
            cardViewTeacher.setOnClickListener {
                val fragment = UserFragment()
                fragment.arguments = Bundle().apply {
                    putString(TOKEN, token)
                    putString(USER, TEACHER)
                }
                gotoFragment(requireActivity(), fragment)

            }
            cardViewClass.setOnClickListener {
                val fragment = ClassFragment()
                fragment.arguments = Bundle().apply {
                    putString(TOKEN, token)
                    putString(USER, CLASS)
                }
                gotoFragment(requireActivity(), fragment)

            }
        }

    }

}