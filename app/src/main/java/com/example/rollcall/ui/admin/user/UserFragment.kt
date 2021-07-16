package com.example.rollcall.ui.admin.user


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemClassAdapter
import com.example.rollcall.adapter.ItemUserAdapter
import com.example.rollcall.databinding.FragmentUserBinding
import com.example.rollcall.ui.admin.createuser.CreateUserFragment
import com.example.rollcall.ui.admin.ediuser.EditUserFragment
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
class UserFragment : BaseFragment<FragmentUserBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_user
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<UserViewModel>()
    private var token: String? = null
    private var user: String? = null

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        getArgument()
        setup()
        clickView()
    }

    //-------------------------------- Func ----------------------------------------
    private fun getArgument() {
        token = arguments?.getString(TOKEN)
        user = arguments?.getString(USER)
    }

    private fun setup() {
        val itemUserAdapter = ItemUserAdapter {
            val fragment = EditUserFragment()
            fragment.arguments = Bundle().apply {
                putString(TOKEN, token)
                putSerializable(USER, it)
            }
            gotoFragment(requireActivity(), fragment)
        }
        baseBinding.apply {
            viewmodel = viewModel
            adapter = itemUserAdapter
        }
        viewModel.apply {
            user?.let { user ->
                title = if(user == STUDENT) "Sinh Viên" else "Giảng Viên"
                token?.let { token ->
                    getUsers(token, user)
                }

            }
            users.observe(viewLifecycleOwner, {
                it?.let {
                    itemUserAdapter.submitList(it.data)
                }
            })
        }
    }

    private fun clickView() {
        baseBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        baseBinding.btnCreateUser.setOnClickListener {
            val fragment = CreateUserFragment()
            fragment.arguments = Bundle().apply {
                putString(TOKEN, token)
                putString(USER, user)
            }
            gotoFragment(requireActivity(), fragment)
        }
    }

}
