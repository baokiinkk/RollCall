package com.example.rollcall.ui.user.listclassess.classInfo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemUserAdapter
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.data.model.Report
import com.example.rollcall.data.model.ReportBody
import com.example.rollcall.data.model.User
import com.example.rollcall.databinding.FragmentClassInfoBinding
import com.example.rollcall.ui.user.report.ReportFragment
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.royrodriguez.transitionbutton.TransitionButton
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ClassInfoFragment : BaseFragment<FragmentClassInfoBinding>(), TimePicker.OnTimeChangedListener {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_class_info
    }
    //-------------------------------- Variable ----------------------------------------
    private var token: String? = null
    private var classInfo: DataClass? = null
    private var user: User? = null
    private lateinit var dialog: BottomSheetDialog
    val viewModel by viewModels<ClassInfoViewModel>()
    private var mIgnoreEvent = false
    private var currentHour = 0
    private var currentMinute = 0
    private var maxHour = 0
    private var maxMinute = 0
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
                    gotoReportFragment(it.data[0])
                else
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun initToolBar() {
        baseBinding.layoutHeader.findViewById<TextView>(R.id.tv_title_toolbar).text =
            "Thông tin lớp"
        val btnDownLoad = baseBinding.layoutHeader.findViewById<TextView>(R.id.btn_download)
        btnDownLoad.visibility = View.VISIBLE
        btnDownLoad.setOnClickListener {
            val websiteIntent = Intent(Intent.ACTION_VIEW).apply {
                viewModel.downLoadAll(classInfo?.id)
                viewModel.LinkUrl.observe(viewLifecycleOwner,{
                    data = Uri.parse(it)
                })
            }
            startActivity(websiteIntent)
        }
    }

    private fun getArgument() {
        token = arguments?.getString(Utils.TOKEN)
        classInfo = arguments?.getSerializable(Utils.CLASS) as DataClass?
        user = arguments?.getSerializable(Utils.USER) as User?
    }

    private fun clickView() {
        baseBinding.layoutHeader.findViewById<TextView>(R.id.btnBack).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        viewModel.users.observe(viewLifecycleOwner,{
            it?.let { report ->
                baseBinding.btnCreateReport.setOnClickListener {
                    if (report.message != null) {
                        dialog = openBottomSheet(requireContext(), layoutInflater)
                        dialog.show()
                    } else {
                        Log.d("quocbao", report.toString())
                        gotoReportFragment(report.data[0])
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
        val btnClose = viewDialog.findViewById<ImageView>(R.id.imageViewClose)
        btnClose.setOnClickListener{
            dialog.dismiss()
        }
        initTime(timePicker)
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

    fun gotoReportFragment(report: Report) {
        val fragment = ReportFragment()
        fragment.arguments = Bundle().apply {
            putString(Utils.TOKEN, token)
            putSerializable(Utils.REPORT, report)
            putSerializable(Utils.USER,user)
        }
        Utils.gotoFragment(requireActivity(), fragment)
    }

    private fun initTime(timePicker: TimePicker) {
        timePicker.setIs24HourView(true)
        setMaxTime()
        timePicker.setOnTimeChangedListener(this)
    }

    private fun setMaxTime() {
        if(classInfo?.shift == "0")
        {
            maxHour = 11
            maxMinute = 15
        } else {
            maxHour = 16
            maxMinute = 30
        }
    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        //init current time
        if (hourOfDay > maxHour || (hourOfDay == maxHour && minute > maxMinute)) {
            view?.currentHour = currentHour
            view?.currentMinute = currentMinute
        }
    }
}