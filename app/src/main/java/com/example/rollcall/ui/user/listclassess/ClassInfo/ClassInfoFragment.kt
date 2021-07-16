package com.example.rollcall.ui.user.listclassess.ClassInfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemUserAdapter
import com.example.rollcall.adapter.SelectItemUserAdapter
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.data.model.User
import com.example.rollcall.databinding.FragmentClassInfoBinding
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassInfoFragment : BaseFragment<FragmentClassInfoBinding>(), View.OnClickListener {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_class_info
    }

    //-------------------------------- Variable ----------------------------------------
    private var token: String? = null
    private var classInfo: DataClass? = null
    private lateinit var dialog: BottomSheetDialog

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
        }
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
        return sheetDialog
    }
}