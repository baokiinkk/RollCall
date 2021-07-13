package com.example.rollcall.ui.admin.createclass


import android.app.DatePickerDialog

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemUserAdapter
import com.example.rollcall.databinding.FragmentCreateClassBinding
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.example.rollcall.utils.Utils.TOKEN
import com.example.rollcall.utils.Utils.diaLogBottom
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
        viewModel.classes.observe(viewLifecycleOwner, {
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
                    startAnimation()
                    token?.let { it1 ->
                        val buoiHoc = baseBinding.spBuoiHoc.editText?.text.toString()
                        val ngayHoc = baseBinding.spNgayHoc.editText?.text.toString()
                        viewModel.createClass(
                            it1,
                            if (buoiHoc == "chiều") "1" else "0",
                            ngayHoc.substring(4)
                        )
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
        val checkTeacher =
            Utils.checkNull(
                viewModel.nameTeacher,
                baseBinding.txtPickTeacher.hint.toString(),
                baseBinding.txtPickTeacher
            )
        val checkBuoiHoc =
            Utils.checkNull(
                baseBinding.spBuoiHoc.editText?.text.toString(),
                baseBinding.spBuoiHoc.hint.toString(),
                baseBinding.spBuoiHoc
            )
        val checkChonNgay =
            Utils.checkNull(
                baseBinding.spNgayHoc.editText?.text.toString(),
                baseBinding.spNgayHoc.hint.toString(),
                baseBinding.spNgayHoc
            )
        val checkDateStart =
            Utils.checkNull(
                viewModel.dateStart,
                baseBinding.txtPickDate.hint.toString(),
                baseBinding.txtPickDate
            )
        val checkId =
            Utils.checkNull(
                viewModel.id,
                baseBinding.textView14.hint.toString(),
                baseBinding.textView14
            )
        val checkName =
            Utils.checkNull(
                viewModel.name,
                baseBinding.textView12.hint.toString(),
                baseBinding.textView12
            )
        val checkPhong =
            Utils.checkNull(
                viewModel.room,
                baseBinding.textView15.hint.toString(),
                baseBinding.textView15
            )
        val checkDays =
            Utils.checkNull(
                viewModel.days,
                baseBinding.textView17.hint.toString(),
                baseBinding.textView17
            )
        val checkCredit =
            Utils.checkNull(
                viewModel.credit,
                baseBinding.textView19.hint.toString(),
                baseBinding.textView19
            )
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
