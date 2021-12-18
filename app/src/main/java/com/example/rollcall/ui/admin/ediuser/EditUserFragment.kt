package com.example.rollcall.ui.admin.ediuser

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.data.model.User
import com.example.rollcall.databinding.FragmentEditUserBinding
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.example.rollcall.utils.Utils.showAlertDialog
import com.google.android.material.datepicker.MaterialDatePicker
import com.royrodriguez.transitionbutton.TransitionButton
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class EditUserFragment : BaseFragment<FragmentEditUserBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_edit_user
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel: EditUserViewModel by viewModels()
    private var token: String? = null
    private var user: User? = null
    private val dfShow =  SimpleDateFormat("yyyy-MM-dd").apply {
        timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
    }

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
                id = it.userId
                lastName = it.lastName
                firstName = it.firstName
                email = it.email
                majorId = it.majorId.majorId
                phone = it.phone
                birthplace = it.birthplace
                sex = if(it.sex == "0") "Nữ" else "Nam"
                birthDay = if(it.birthDate != null) dfShow.format(dfShow.parse(it.birthDate)) else ""
            }
        }
        token?.let { viewModel.getMajor(it) }
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
        baseBinding.inputDate.setOnClickListener {
            dateOfBirth(it)
        }
        baseBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        baseBinding.btnDelete.apply {
            setOnClickListener {
                showAlertDialog(context, "Thông báo", "Bạn có muốn xóa!") {
                    startAnimation()
                    token?.let {
                        viewModel.deleteUser(it)
                    }
                }
            }
        }
    }

    private fun checkValidate(): Boolean {
        clearError()
        val checkId = Utils.checkNull(baseBinding.edtId)
        val checkEmail = Utils.checkNull(baseBinding.edtEmail)
        val checkFirstName = Utils.checkNull(baseBinding.edtfirstName)
        val checkLastName = Utils.checkNull(baseBinding.edtlastName)
        return checkEmail  && checkId && checkFirstName && checkLastName
    }

    private fun clearError() {
        baseBinding.apply {
            edtEmail.error = null
            edtId.error = null
            edtfirstName.error = null
            edtlastName.error = null
        }
    }

    private fun dateOfBirth(view:View) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setTitleText("Add date of birth").build()

        datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
        datePicker.addOnPositiveButtonClickListener {
            val date = Date(it)
            viewModel.datePicker = date
            baseBinding.inputDate.setText(dfShow.format(date))
        }

    }
}