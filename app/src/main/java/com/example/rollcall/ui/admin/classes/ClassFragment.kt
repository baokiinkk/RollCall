package com.example.rollcall.ui.admin.classes


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemClassAdapter
import com.example.rollcall.adapter.ItemUserAdapter
import com.example.rollcall.databinding.FragmentClassBinding
import com.example.rollcall.databinding.FragmentUserBinding
import com.example.rollcall.ui.admin.createclass.CreateClassFragment
import com.example.rollcall.ui.admin.createuser.CreateUserFragment
import com.example.rollcall.ui.admin.ediuser.EditUserFragment
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.example.rollcall.utils.Utils.CLASS
import com.example.rollcall.utils.Utils.TEACHER
import com.example.rollcall.utils.Utils.TOKEN
import com.example.rollcall.utils.Utils.USER
import com.example.rollcall.utils.Utils.gotoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassFragment : BaseFragment<FragmentClassBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_class
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<ClassViewModel>()
    private var token: String? = null

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        getArgument()
        setup()
        clickView()
    }

    //-------------------------------- Func ----------------------------------------
    private fun getArgument() {
        token = arguments?.getString(TOKEN)
    }

    private fun setup() {
        val itemClassAdapter = ItemClassAdapter {

        }
        baseBinding.apply {
            adapter = itemClassAdapter
        }
        viewModel.apply {
                token?.let { token ->
                    getUsers(token)
            }
            classes.observe(viewLifecycleOwner, {
                it?.let {
                    itemClassAdapter.submitList(it.data)
                }
            })
        }
    }

    private fun clickView() {
        baseBinding.btnCreateUser.setOnClickListener {
            val fragment = CreateClassFragment()
            fragment.arguments = Bundle().apply {putString(TOKEN,token)}
            gotoFragment(requireActivity(),fragment)
        }
    }

}
