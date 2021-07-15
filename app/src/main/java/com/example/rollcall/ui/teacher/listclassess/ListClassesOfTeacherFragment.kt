package com.example.rollcall.ui.teacher.listclassess

import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.databinding.FragmentListClassesOfTeacherBinding
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListClassesOfTeacherFragment : BaseFragment<FragmentListClassesOfTeacherBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_list_classes_of_teacher
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel: ListClassesOfTeacherViewModel by viewModels()
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