package com.example.rollcall.ui.user.listclassess.classInfo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemUserAdapter
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.data.model.ReportBody
import com.example.rollcall.databinding.FragmentClassInfoBinding
import com.example.rollcall.ui.user.report.ReportFragment
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.royrodriguez.transitionbutton.TransitionButton
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ClassInfoFragment : BaseFragment<FragmentClassInfoBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_class_info
    }

    //-------------------------------- Variable ----------------------------------------
    private var token: String? = null
    private var classInfo: DataClass? = null
    private lateinit var dialog: BottomSheetDialog
    val viewModel by viewModels<ClassInfoViewModel>()

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        getArgument()
        initToolBar()
        setup()
        clickView()
    }

    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        val itemUserAdapter = ItemUserAdapter {
        }
        itemUserAdapter.submitList(classInfo?.students)
        baseBinding.apply {
            adapter = itemUserAdapter
            viewmodel = viewModel
            viewModel.classinfor = classInfo
        }
        viewModel.getReport(classInfo?.id, token, getTimeCurrent())
        viewModel.getUsers.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                if (it.message == null)
                    gotoReportFragment(it.data[0].id)
                else
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initToolBar() {
        baseBinding.layoutHeader.findViewById<TextView>(R.id.tv_title_toolbar).text =
            "Thông tin lớp"
    }

    private fun getArgument() {
        token = arguments?.getString(Utils.TOKEN)
        classInfo = arguments?.getSerializable(Utils.CLASS) as DataClass?
    }

    private fun clickView() {
        baseBinding.layoutHeader.findViewById<TextView>(R.id.btnBack).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        viewModel.users.observe(viewLifecycleOwner,{
            it?.let {report->
                baseBinding.btnCreateReport.setOnClickListener {
                    if (report.message != null) {
                        dialog = openBottomSheet(requireContext(), layoutInflater)
                        dialog.show()
                    } else {
                        Log.d("quocbao",report.toString())
                       gotoReportFragment(report.data[0].id)
                    }
                }
            }
        })

    }


    private fun openBottomSheet(
        context: Context,
        layoutInflater: LayoutInflater,

        ): BottomSheetDialog {
        val sheetDialog = BottomSheetDialog(context)
        val viewDialog: View = layoutInflater.inflate(R.layout.bottom_sheet_edit_report, null)
        val timePicker = viewDialog.findViewById<TimePicker>(R.id.picker_pickTime)
        timePicker.setIs24HourView(true)
        sheetDialog.setContentView(viewDialog)
        val check = sheetDialog.findViewById<CheckBox>(R.id.tv_allow_late)
        sheetDialog.findViewById<TransitionButton>(R.id.button_confirm)?.setOnClickListener {
            val time = "${timePicker.hour}:${timePicker.minute}"
            val data = ReportBody(time, check?.isChecked.toString())
            viewModel.createReport(classInfo?.id, data, token)
        }
        return sheetDialog
    }

    fun getTimeCurrent(): String {
        val date = Calendar.getInstance()
        date.timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
        val year = date.get(Calendar.YEAR)
        val month = date.get(Calendar.MONTH) + 1
        val dayOfMonth = date.get(Calendar.DAY_OF_MONTH)
        val todate = if (month < 10) "0$month" else "$month"
        return "$dayOfMonth-$todate-$year"
    }

    fun gotoReportFragment(id: String) {
        val fragment = ReportFragment()
        fragment.arguments = Bundle().apply {
            putString(Utils.TOKEN, token)
            putSerializable(Utils.CLASS, id)
        }
        Utils.gotoFragment(requireActivity(), fragment)
    }
}