package com.example.rollcall.ui.admin.createuser

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.databinding.FragmentCreateUserBinding
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.example.rollcall.utils.Utils.checkNull
import com.royrodriguez.transitionbutton.TransitionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateUserFragment : BaseFragment<FragmentCreateUserBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_create_user
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel: CreateUserViewModel by viewModels()
    lateinit var listMajor:List<String>
    private var token: String? = null
    private var user: String? = null

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        getArgument()
        setup()
        clickView()
    }

    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        baseBinding.apply {
            viewmodel = viewModel
        }
        viewModel.users.observe(viewLifecycleOwner, {
            it?.let {
                if (it) {
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
                    Toast.makeText(context, "Tạo không thành công", Toast.LENGTH_SHORT).show()
                }
            }
        })
        token?.let { viewModel.getMajor(it) }
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
        baseBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun checkValidate(): Boolean {
        clearValidate()
        val checkId = checkNull(baseBinding.edtId)
        val checkPassword = checkNull(baseBinding.edtPassword)
        val checkEmail = checkNull(baseBinding.edtEmail)
        val checkFirstName = checkNull(baseBinding.edtfirstName)
        val checkLastName = checkNull(baseBinding.edtlastName)
        return checkEmail && checkPassword && checkId && checkFirstName && checkLastName
    }
    private fun clearValidate(){
        baseBinding.apply {
            edtEmail.error = null
            edtId.error = null
            edtPassword.error = null
            edtfirstName.error = null
            edtlastName.error = null
        }
    }

}