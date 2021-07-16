package com.example.rollcall.ui.user.listclassess.ClassInfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemUserAdapter
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.databinding.FragmentClassInfoBinding
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class ClassInfoFragment : BaseFragment<FragmentClassInfoBinding>(), View.OnClickListener, TimePicker.OnTimeChangedListener {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_class_info
    }

    //-------------------------------- Variable ----------------------------------------
    private var token: String? = null
    private var classInfo: DataClass? = null
    private lateinit var dialog: BottomSheetDialog
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
        }

        baseBinding.layoutClassInfo.findViewById<TextView>(R.id.tv_classID_content).text =
            classInfo?.id ?: ""
        baseBinding.layoutClassInfo.findViewById<TextView>(R.id.tv_className_content).text =
            classInfo?.name ?: ""
        baseBinding.layoutClassInfo.findViewById<TextView>(R.id.tv_classSize_content).text =
            classInfo?.students?.size.toString()
    }

    private fun initToolBar() {
        baseBinding.layoutHeader.findViewById<TextView>(R.id.tv_title_toolbar).text = "Thông tin lớp"
    }

    private fun getArgument() {
        token = arguments?.getString(Utils.TOKEN)
        classInfo = arguments?.getSerializable(Utils.CLASS) as DataClass?
    }

    private fun clickView() {
        baseBinding.layoutHeader.findViewById<TextView>(R.id.btnBack).setOnClickListener(this)
        baseBinding.btnCreateReport.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnBack -> {
                requireActivity().onBackPressed()
            }
            R.id.btn_create_report -> {
                dialog = openBottomSheet(requireContext(), layoutInflater)
                dialog.show()
            }
            R.id.imageViewClose -> {
                dialog.dismiss()
            }
        }
    }


    private fun openBottomSheet(
        context: Context,
        layoutInflater: LayoutInflater,

    ): BottomSheetDialog {
        val sheetDialog = BottomSheetDialog(context)
        val viewDialog: View = layoutInflater.inflate(R.layout.bottom_sheet_edit_report, null)
        val timePicker = viewDialog.findViewById<TimePicker>(R.id.picker_pickTime)
        val btnClose = viewDialog.findViewById<ImageView>(R.id.imageViewClose)
        btnClose.setOnClickListener(this)
        initTime(timePicker)
        sheetDialog.setContentView(viewDialog)
        return sheetDialog
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
            maxHour = 4
            maxMinute = 30
        }
    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val datetime: Calendar = Calendar.getInstance()
        currentHour = datetime.get(Calendar.HOUR_OF_DAY)
        currentMinute = datetime.get(Calendar.MINUTE)
        //init current time
        view?.currentHour = currentHour
        view?.currentMinute = currentMinute
        if (hourOfDay > maxHour) {
            view?.currentHour = currentHour
            view?.currentMinute = currentMinute

        } else if (hourOfDay == maxHour && minute > maxMinute) {
            view?.currentHour = currentHour
            view?.currentMinute = currentMinute
        }
    }

    override fun onStop() {
        super.onStop()
        dialog.dismiss()
    }
}