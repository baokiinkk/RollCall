package com.example.rollcall.ui.user.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.data.model.User
import com.example.rollcall.databinding.FragmentHomeUserBinding
import com.example.rollcall.ui.user.CheckQR.CheckinQRCodeFragment
import com.example.rollcall.ui.user.info.InfoUserFragment
import com.example.rollcall.ui.user.listclassess.ListClassesFragment
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeUserFragment : BaseFragment<FragmentHomeUserBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home_user
    }
    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<HomeUserViewModel>()
    private var token: String? = null
    private var user: User? = null
    private val fragListClass = ListClassesFragment()
    private val fragQRScan = CheckinQRCodeFragment()
    private val fragInfo = InfoUserFragment()

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        getArgument()
        setup()
        setEvent()
    }

    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        baseBinding.apply {
            viewmodel = viewModel
        }
        baseBinding.bottomNavMain.selectedItemId = R.id.navInfo
        setCurrentFragment(requireActivity(),fragInfo)
    }

    private fun getArgument() {
        token = arguments?.getString(Utils.TOKEN)
        user = arguments?.getSerializable(Utils.USER) as User?
    }

    private fun setEvent() {
        baseBinding.bottomNavMain.setOnNavigationItemSelectedListener { menuItem ->
            clickNavigateSubScreen(menuItem)
        }


    }

    private fun clickNavigateSubScreen(menuItem: MenuItem): Boolean {

        return when (menuItem.itemId) {
            R.id.navListClasses -> {
                setCurrentFragment(requireActivity(),fragListClass)
                true
            }

            R.id.navInfo -> {
                setCurrentFragment(requireActivity(),fragInfo)
                true
            }

            R.id.navQR -> {
                setCurrentFragment(requireActivity(),fragQRScan)
                true
            }

            else -> false
        }
    }

    private fun setCurrentFragment(activity: FragmentActivity, fragment: Fragment) {
        fragment.arguments =
            Bundle().apply {
                putString(Utils.TOKEN, token)
                putSerializable(Utils.USER, user)}

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragContainer, fragment)
            .commit()
    }

}