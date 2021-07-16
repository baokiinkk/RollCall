package com.example.rollcall.ui.teacher.listclassess.ClassInfo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemClassAdapter
import com.example.rollcall.adapter.ItemUserAdapter
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.databinding.FragmentClassInfoBinding
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Singleton

@AndroidEntryPoint
class ClassInfoFragment : BaseFragment<FragmentClassInfoBinding>(), View.OnClickListener {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_class_info
    }

    //-------------------------------- Variable ----------------------------------------
    private var token: String? = null
    private var classInfo: DataClass? = null

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
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnBack -> {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }
}