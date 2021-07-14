package com.example.rollcall.ui.admin.createclass


import android.app.DatePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemUserAdapter
import com.example.rollcall.adapter.SelectItemUserAdapter
import com.example.rollcall.data.model.Class
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.databinding.FragmentCreateClassBinding
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.example.rollcall.utils.Utils.TOKEN
import com.example.rollcall.utils.Utils.diaLogBottom
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.royrodriguez.transitionbutton.TransitionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateClassFragment : BaseFragment<FragmentCreateClassBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_create_class
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<CreateClassViewModel>()
    private var token: String? = null
    lateinit var adapterUser: ItemUserAdapter
    lateinit var dialog: BottomSheetDialog

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
        }
        viewModel.apply {
            token?.let { token ->
                getUsers(token)
            }
        }
        adapterUser = ItemUserAdapter {
            baseBinding.txtPickTeacher.text = it.name
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
        val selectedYear = 2000
        val selectedMonth = 5
        val selectedDayOfMonth = 10
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                baseBinding.txtPickDate.setText(
                    dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                )
            }
        baseBinding.txtPickDate.apply {
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
                baseBinding.txtPickTeacher.apply {
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
                        viewModel.createClass(
                            it1,
                            baseBinding.spBuoiHoc.selectedItemPosition.toString(),
                            (baseBinding.spNgayHoc.selectedItemPosition + 2).toString()
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
        val checkDateStart = baseBinding.txtPickDate.text != ""
        val checkTeacher = baseBinding.txtPickTeacher.text != ""

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
        return checkDateStart && checkTeacher && checkId && checkName && checkCredit && checkPhong && checkDays
    }

}
