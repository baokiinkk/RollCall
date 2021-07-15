package com.example.rollcall.ui.teacher.listclassess.ClassInfo

import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.databinding.FragmentClassInfoBinding
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils

class ClassInfoFragment : BaseFragment<FragmentClassInfoBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_class_info
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel: ClassInfoViewModel by viewModels()
    private var token: String? = null
    private var user: String? = null

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        setup()
        getArgument()
        clickView()
    }
    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        baseBinding.apply {
//            viewmodel = viewModel
//            adapter = itemClassAdapter
        }

        // observe list class
    }

    private fun getArgument() {
        token = arguments?.getString(Utils.TOKEN)
        user = arguments?.getString(Utils.USER)
    }

    private fun clickView() {
    }
}