package com.example.rollcall.ui.admin.home

import android.util.Log
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemHomeAdapter
import com.example.rollcall.databinding.FragmentHomeAdminBinding
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils.TAG
import com.example.rollcall.utils.Utils.TOKEN
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeAdminFragment :BaseFragment<FragmentHomeAdminBinding>(){
    override fun getLayoutRes(): Int {
        return R.layout.fragment_home_admin
    }
    val viewMode by viewModels<HomeAdminViewModel>()
    private var token:String? =null
    override fun onCreateViews() {
        token = arguments?.getString(TOKEN)
        val itemHomeAdapter = ItemHomeAdapter{

        }
        baseBinding.apply {
            viewmodel = viewMode
            adapter = itemHomeAdapter
        }
        itemHomeAdapter.submitList(viewMode.getData())

    }

}