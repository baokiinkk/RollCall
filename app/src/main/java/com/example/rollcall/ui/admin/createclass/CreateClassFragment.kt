package com.example.rollcall.ui.admin.createclass


import android.app.DatePickerDialog
import android.os.Bundle

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemUserAdapter
import com.example.rollcall.databinding.FragmentCreateClassBinding
import com.example.rollcall.ui.admin.choosestudent.ChooseStudentFragment
import com.example.rollcall.ui.admin.choosestudent.ChooseStudentViewModel
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.example.rollcall.utils.Utils.CLASS
import com.example.rollcall.utils.Utils.TOKEN
import com.example.rollcall.utils.Utils.diaLogBottom
import com.example.rollcall.utils.Utils.gotoFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.royrodriguez.transitionbutton.TransitionButton
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class CreateClassFragment : BaseFragment<FragmentCreateClassBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_create_class
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<CreateClassViewModel>()
    private var token: String? = null
    private lateinit var adapterUser: ItemUserAdapter
    private lateinit var dialog: BottomSheetDialog

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
        baseBinding.apply {
            viewmodel = viewModel

//
        }
        viewModel.apply {
            token?.let { token ->
                getUsers(token)
            }
        }
        adapterUser = ItemUserAdapter {
            baseBinding.txtPickTeacher.editText?.setText(it.name)
            viewModel.datateacher = it
            dialog.dismiss()
        }
    }

    private fun clickView() {
        val date = Calendar.getInstance()
        date.timeZone = TimeZone.getTimeZone("UTC")
        val selectedYear = date.get(Calendar.YEAR)
        val selectedMonth = date.get(Calendar.MONTH)
        val selectedDayOfMonth = date.get(Calendar.DAY_OF_MONTH)
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                baseBinding.txtPickDate.editText?.setText(
                    "$dayOfMonth-$monthOfYear-$year"
                )

            }
        baseBinding.txtPickDate.editText?.apply {
            setOnClickListener {
                val datePickerDialog = DatePickerDialog(
                    context,
                    android.R.style.Theme_Material_Dialog,
                    dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth
                )
                datePickerDialog.show()
            }
        }

        viewModel.teacher.observe(viewLifecycleOwner, {
            it?.let { users ->
                baseBinding.txtPickTeacher.editText?.apply {
                    setOnClickListener {
                        users.data?.let { users ->
                            dialog = diaLogBottom(
                                context, layoutInflater,
                                users, adapterTeacher = adapterUser
                            )
                            dialog.show()
                        }
                    }
                }
            }
        })

        baseBinding.btnCreate.apply {
            setOnClickListener {
                if (checkValidate()) {
                    token?.let { token ->
                        val buoiHoc = baseBinding.spBuoiHoc.editText?.text.toString()
                        val ngayHoc = baseBinding.spNgayHoc.editText?.text.toString()

                        val fragment = ChooseStudentFragment()
                        fragment.arguments = Bundle().apply {
                            putString(TOKEN, token)
                            putSerializable(
                                CLASS, viewModel.createClass(
                                    if (buoiHoc == "chiều") "1" else "0",
                                    ngayHoc.substring(4)
                                )
                            )
                        }
                        gotoFragment(requireActivity(), fragment)
                    }
                } else {
                    Toast.makeText(context, "Không được để trống dữ liệu", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        baseBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun checkValidate(): Boolean {
        clearError()
        val checkTeacher = Utils.checkNull(baseBinding.txtPickTeacher)
        val checkBuoiHoc = Utils.checkNull(baseBinding.spBuoiHoc)
        val checkChonNgay = Utils.checkNull(baseBinding.spNgayHoc)
        val checkDateStart = Utils.checkNull(baseBinding.txtPickDate)
        val checkId = Utils.checkNull(baseBinding.textView14)
        val checkName = Utils.checkNull(baseBinding.textView12)
        val checkPhong = Utils.checkNull(baseBinding.textView15)
        val checkDays = Utils.checkNull(baseBinding.textView17)
        val checkCredit = Utils.checkNull(baseBinding.textView19)
        return checkChonNgay && checkBuoiHoc && checkDateStart && checkTeacher && checkId && checkName && checkCredit && checkPhong && checkDays
    }

    private fun clearError() {
        baseBinding.apply {
            textView14.error = null
            textView12.error = null
            textView15.error = null
            textView17.error = null
            textView19.error = null
            spBuoiHoc.error = null
            spNgayHoc.error = null
            txtPickDate.error = null
            txtPickTeacher.error = null
        }
    }

}
