package com.example.rollcall.ui.admin.user


import android.util.Log
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemUserAdapter
import com.example.rollcall.databinding.FragmentUserBinding
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.example.rollcall.utils.Utils.USER
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
    }

    //-------------------------------- Func ----------------------------------------
    private fun getArgument() {
        token = arguments?.getString(Utils.TOKEN)
        user = arguments?.getString(USER)
    }

    private fun setup() {
        val itemUserAdapter = ItemUserAdapter {
        }
        baseBinding.apply {
            viewmodel = viewModel
            adapter = itemUserAdapter
        }
        viewModel.apply {
            token?.let { user?.let { it1 -> getUsers(it, it1) } }
            users.observe(viewLifecycleOwner, {
                it?.let {
                    itemUserAdapter.submitList(it.data)
                }
            })
        }
    }

}
