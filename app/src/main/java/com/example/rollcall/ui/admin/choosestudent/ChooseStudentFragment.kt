package com.example.rollcall.ui.admin.choosestudent


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.rollcall.R
import com.example.rollcall.adapter.SelectItemUserAdapter
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.databinding.FragmentChooseStudentBinding
import com.example.rollcall.ui.admin.home.HomeAdminFragment
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.example.rollcall.utils.Utils.CLASS
import com.example.rollcall.utils.Utils.TOKEN
import com.royrodriguez.transitionbutton.TransitionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseStudentFragment : BaseFragment<FragmentChooseStudentBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_choose_student
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<ChooseStudentViewModel>()
    private var token: String? = null
    private var dataClass: DataClass? = null
    val selectItemUserAdapter: SelectItemUserAdapter by lazy {
        SelectItemUserAdapter()
    }

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
            adapter = selectItemUserAdapter
        }
        viewModel.apply {
            token?.let { token ->
                getUsers(token)
            }
            users.observe(viewLifecycleOwner, {
                it?.let {
                    selectItemUserAdapter.submitList(it.data)
                }
            })
            classes.observe(viewLifecycleOwner, Observer {
                it?.let {
                    if (it.message == null) {
                        baseBinding.btnCreate.stopAnimation(
                            TransitionButton.StopAnimationStyle.EXPAND
                        ) {
                            requireActivity().supportFragmentManager.popBackStack()
                            requireActivity().supportFragmentManager.popBackStack()
                        }
                        Toast.makeText(context,"Tạo thành công",Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                        baseBinding.btnCreate.stopAnimation(
                            TransitionButton.StopAnimationStyle.SHAKE,
                            null
                        )
                    }
                }
            })
        }
    }

    private fun clickView() {
        baseBinding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        baseBinding.btnCreate.apply {
            setOnClickListener {
                startAnimation()
                val tmp = selectItemUserAdapter.currentList.filter {
                    it.selected
                }.toMutableList()
                dataClass?.let { dataClass ->
                    dataClass.students = tmp
                    token?.let { token ->
                        viewModel.createClass(token, dataClass)
                    }
                }
            }
        }
    }

}
