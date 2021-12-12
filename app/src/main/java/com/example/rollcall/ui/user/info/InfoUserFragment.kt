package com.example.rollcall.ui.user.info

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.data.model.User
import com.example.rollcall.databinding.FragmentInfoUserBinding
import com.example.rollcall.ui.login.LoginFragment
import com.example.rollcall.ui.user.CheckQR.CheckinQRCodeFragment
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoUserFragment : BaseFragment<FragmentInfoUserBinding>() {

    companion object{
        fun instance(token:String?,user:User?) : InfoUserFragment{
            val bundle = Bundle().apply {
                putString(Utils.TOKEN, token)
                putSerializable(Utils.USER, user)}
            val fragment = InfoUserFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun getLayoutRes(): Int {
        return R.layout.fragment_info_user
    }
    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<InfoUserViewModel>()
    private var token: String? = null
    private var user: User? = null

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        getArgument()
        setup()
        getData()
        clickView()
    }

    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        if(user?.role == Utils.STUDENT) {
            baseBinding.buttonQrCode.visibility = View.VISIBLE
            baseBinding.lop.visibility = View.VISIBLE
            baseBinding.khoahoc.visibility = View.VISIBLE
            baseBinding.txtMaSVTitle.text = "Mã SV"
            baseBinding.txtTenSVTitle.text = "Tên SV"
        }
        baseBinding.apply {
            viewmodel = viewModel
        }
    }

    private fun getData() {

        viewModel.getInfoUser(token,user?.id)
        viewModel.isLogOut.observe(viewLifecycleOwner,{
            it?.let {
                Utils.gotoFragment(requireActivity(),LoginFragment())
            }
        })
    }

    private fun getArgument() {
        token = arguments?.getString(Utils.TOKEN)
        user = arguments?.getSerializable(Utils.USER) as User?
    }

    private fun clickView() {
        baseBinding.buttonQrCode.setOnClickListener {
            val fragment = CheckinQRCodeFragment()
            fragment.arguments = Bundle().apply {
                putString(Utils.TOKEN, token)
                putSerializable(Utils.USER, user)
            }
            Utils.gotoFragment(requireActivity(),fragment)
        }
    }
}