package com.example.rollcall.ui.teacher.listclassess

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemClassAdapter
import com.example.rollcall.data.model.User
import com.example.rollcall.databinding.FragmentListClassesOfTeacherBinding
import com.example.rollcall.ui.teacher.listclassess.ClassInfo.ClassInfoFragment
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
    private var user: User? = null

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        getArgument()
        setup()
        clickView()
    }
    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        val itemClassAdapter = ItemClassAdapter {
            val fragment = ClassInfoFragment()
            fragment.arguments = Bundle().apply {
                putString(Utils.TOKEN, token)
                putSerializable(Utils.CLASS, it)
            }
            setCurrentFragment(requireActivity(), fragment)
        }
        baseBinding.apply {
            viewmodel = viewModel
            adapter = itemClassAdapter
        }

        // observe list class
        token?.let { user?.let { it1 -> viewModel.getListClassTeacher(it, it1.id) } }
        viewModel.classes.observe(viewLifecycleOwner, {
            it?.let {
                Log.i("class", it.data.toString())
                itemClassAdapter.submitList(it.data)

            }
        })
    }

    private fun setCurrentFragment(activity: FragmentActivity, fragment: Fragment) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun getArgument() {
        token = arguments?.getString(Utils.TOKEN)
        user = arguments?.getSerializable(Utils.USER) as User?
        Log.d("token", token + " " + user.toString())
    }

    private fun clickView() {
    }

    override fun onResume() {
        super.onResume()
        setup()
    }
}