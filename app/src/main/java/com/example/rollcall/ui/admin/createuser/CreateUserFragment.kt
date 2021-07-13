package com.example.rollcall.ui.admin.createuser

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.rollcall.R
import com.example.rollcall.databinding.FragmentCreateUserBinding
import com.example.rollcall.ui.admin.home.HomeAdminFragment
import com.example.rollcall.ui.admin.user.UserFragment
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.example.rollcall.utils.Utils.checkNull
import com.google.android.material.textfield.TextInputEditText
import com.royrodriguez.transitionbutton.TransitionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateUserFragment : BaseFragment<FragmentCreateUserBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_create_user
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel: CreateUserViewModel by viewModels()
    private var token: String? = null
    private var user: String? = null


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
                    baseBinding.btnCreate.stopAnimation(
                        TransitionButton.StopAnimationStyle.EXPAND
                    ) {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                    Toast.makeText(context, "Thành Công", Toast.LENGTH_SHORT).show()
                } else {
                    baseBinding.btnCreate.stopAnimation(
                        TransitionButton.StopAnimationStyle.SHAKE,
                        null
                    )
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getArgument() {
        token = arguments?.getString(Utils.TOKEN)
        user = arguments?.getString(Utils.USER)
    }

    private fun clickView() {
        baseBinding.btnCreate.apply {
            setOnClickListener {
                if (checkValidate()) {
                    startAnimation()
                    token?.let { it1 -> user?.let { it2 -> viewModel.createUser(it1, it2) } }
                }
            }
        }
    }

    private fun checkValidate(): Boolean {
        val checkId = checkNull(viewModel.id, baseBinding.edtId.hint.toString(), baseBinding.edtId)
        val checkPassword = checkNull(
            viewModel.password,
            baseBinding.edtPassword.hint.toString(),
            baseBinding.edtPassword
        )
        val checkEmail =
            checkNull(viewModel.email, baseBinding.edtEmail.hint.toString(), baseBinding.edtEmail)
        val checkName =
            checkNull(viewModel.name, baseBinding.edtname.hint.toString(), baseBinding.edtname)
        return checkEmail && checkPassword && checkId && checkName
    }


}