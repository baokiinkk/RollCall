package com.example.rollcall.ui.admin.ediuser

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.rollcall.R
import com.example.rollcall.data.model.User
import com.example.rollcall.databinding.FragmentCreateUserBinding
import com.example.rollcall.databinding.FragmentEditUserBinding
import com.example.rollcall.ui.admin.home.HomeAdminFragment
import com.example.rollcall.ui.admin.user.UserFragment
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.royrodriguez.transitionbutton.TransitionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditUserFragment : BaseFragment<FragmentEditUserBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_edit_user
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel: EditUserViewModel by viewModels()
    private var token: String? = null
    private var user: User? = null


    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        setup()
        getArgument()
        clickView()
    }


    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        baseBinding.apply {
            viewmodel = viewModel
        }

        viewModel.users.observe(viewLifecycleOwner, {
            it?.let {
                if (it.message == null) {
                    baseBinding.btnOk.stopAnimation(
                        TransitionButton.StopAnimationStyle.EXPAND
                    ) {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                    Toast.makeText(context, "Thành Công", Toast.LENGTH_SHORT).show()
                } else {
                    baseBinding.btnOk.stopAnimation(
                        TransitionButton.StopAnimationStyle.SHAKE,
                        null
                    )
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.isDelete.observe(viewLifecycleOwner, {
            it?.let {
                if (it.message != "0") {
                    baseBinding.btnDelete.stopAnimation(
                        TransitionButton.StopAnimationStyle.EXPAND
                    ) {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                    Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show()
                } else {
                    baseBinding.btnOk.stopAnimation(
                        TransitionButton.StopAnimationStyle.SHAKE,
                        null
                    )
                    Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getArgument() {
        token = arguments?.getString(Utils.TOKEN)
        user = arguments?.getSerializable(Utils.USER) as User?
        user?.let {
            viewModel.apply {
                id = it.id
                name = it.name
                email = it.email
            }
        }
    }

    private fun clickView() {
        baseBinding.btnOk.apply {
            setOnClickListener {
                if (checkValidate()) {
                    startAnimation()
                    token?.let {
                        viewModel.editUser(it)
                    }
                }
            }
        }
        baseBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        baseBinding.btnDelete.apply {
            setOnClickListener {
                startAnimation()
                token?.let {
                    viewModel.deleteUser(it)
                }
            }
        }
    }

    private fun checkValidate(): Boolean {
        val checkId =
            Utils.checkNull(baseBinding.edtId)
        val checkEmail = Utils.checkNull(baseBinding.edtEmail)
        val checkName = Utils.checkNull(baseBinding.edtname)
        return checkEmail && checkId && checkName
    }

}