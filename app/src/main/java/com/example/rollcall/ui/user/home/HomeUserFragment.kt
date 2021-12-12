package com.example.rollcall.ui.user.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ViewPageAdapter
import com.example.rollcall.data.model.User
import com.example.rollcall.databinding.FragmentHomeUserBinding
import com.example.rollcall.ui.user.CheckQR.CheckinQRCodeFragment
import com.example.rollcall.ui.user.info.InfoUserFragment
import com.example.rollcall.ui.user.listclassess.ListClassesFragment
import com.example.rollcall.ui.user.mainboard.MainBoardFragment
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import eu.long1.spacetablayout.SpaceTabLayout

@AndroidEntryPoint
class HomeUserFragment : BaseFragment<FragmentHomeUserBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home_user
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<HomeUserViewModel>()
    private var token: String? = null
    private var user: User? = null

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        getArgument()
        setup()
    }


    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        baseBinding.apply {
            viewmodel = viewModel
        }
        val fragmentList: MutableList<Fragment> = ArrayList()
        if(user?.role == Utils.STUDENT) {
            fragmentList.add(MainBoardFragment())
        }
        fragmentList.add(ListClassesFragment.instance(token, user))
        fragmentList.add(InfoUserFragment.instance(token, user))
        viewModel.adapter =
            ViewPageAdapter(fragmentList, requireActivity())
    }

    private fun getArgument() {
        token = arguments?.getString(Utils.TOKEN)
        user = arguments?.getSerializable(Utils.USER) as User?
    }

}