package com.example.rollcall.ui.admin.editclass


import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemUserAdapter
import com.example.rollcall.adapter.SelectItemUserAdapter
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.databinding.FragmentCreateClassBinding
import com.example.rollcall.databinding.FragmentEditClassBinding
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
class EditClassFragment : BaseFragment<FragmentEditClassBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_edit_class
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<EditClassViewModel>()
    private var token: String? = null
    private lateinit var adapterUser: ItemUserAdapter
    private lateinit var adapterSelect: SelectItemUserAdapter
    private lateinit var dialog: BottomSheetDialog
    private var dataClass: DataClass? = null

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        getArgument()
        setup()
        clickView()
    }

    //-------------------------------- Func ----------------------------------------
    private fun getArgument() {
        token = arguments?.getString(TOKEN)
        dataClass = requireArguments().getSerializable(CLASS) as DataClass?
    }

    private fun setup() {
        baseBinding.apply {
            viewmodel = viewModel
        }
        viewModel.apply {
            getData(dataClass, token)


            classes.observe(viewLifecycleOwner, {
                it?.let {
                    if (it.message == null) {
                        baseBinding.btnOk.stopAnimation(
                            TransitionButton.StopAnimationStyle.EXPAND
                        ) {
                            requireActivity().supportFragmentManager.popBackStack()
                        }
                        Toast.makeText(context, "Tạo thành công", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        baseBinding.btnOk.stopAnimation(
                            TransitionButton.StopAnimationStyle.SHAKE,
                            null
                        )
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
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
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
        adapterUser = ItemUserAdapter {
            baseBinding.txtPickTeacher.editText?.setText(it.name)
            viewModel.datateacher = it
            dialog.dismiss()
        }
        adapterSelect = SelectItemUserAdapter()
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

        baseBinding.btnOk.apply {
            setOnClickListener {
                startAnimation()
                if (checkValidate()) {
                    token?.let { token ->
                        viewModel.editClass(token)
                    }
                } else {
                    Toast.makeText(context, "Không được để trống dữ liệu", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        baseBinding.btnDelete.apply {
            setOnClickListener {
                startAnimation()
                token?.let {
                    viewModel.deleteClass(it, dataClass)
                }
            }
        }

        baseBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        baseBinding.btnStudent.setOnClickListener {
            dataClass?.students?.let { it1 ->
                dialog = diaLogBottom(
                    requireContext(), layoutInflater,
                    it1, adapterStudent = adapterSelect
                )
                dialog.show()
            }

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
