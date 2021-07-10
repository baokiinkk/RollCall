package com.example.rollcall.ui.admin.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.databinding.FragmentHomeAdminBinding
import com.example.rollcall.ui.admin.user.UserFragment
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils.STUDENT
import com.example.rollcall.utils.Utils.TEACHER
import com.example.rollcall.utils.Utils.TOKEN
import com.example.rollcall.utils.Utils.USER
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeAdminFragment : BaseFragment<FragmentHomeAdminBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_home_admin
    }

    val viewMode by viewModels<HomeAdminViewModel>()
    private var token: String? = null
    override fun onCreateViews() {
        token = arguments?.getString(TOKEN)

        baseBinding.apply {
            viewmodel = viewMode
        }
        token?.let { viewMode.getData(it) }
        viewMode.dashBoard.observe(viewLifecycleOwner,{
            it?.let {
                Log.d("quocbao",it.toString())
            }
        })
        onClick()
    }

    fun onClick() {
        baseBinding.apply {
            cardViewStudent.setOnClickListener {
                val fragment = UserFragment()
                fragment.arguments = Bundle().apply {
                    putString(TOKEN, token)
                    putString(USER, STUDENT)
                }
                gotoFragment(fragment)
            }
            cardViewTeacher.setOnClickListener {
                val fragment = UserFragment()
                fragment.arguments = Bundle().apply {
                    putString(TOKEN, token)
                    putString(USER, TEACHER)
                }
                gotoFragment(fragment)

            }
        }

    }

    fun gotoFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(HomeAdminFragment::class.java.simpleName)
            .commit()
    }

}