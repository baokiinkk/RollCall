package com.example.rollcall.ui.user.listclassess

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemClassAdapter
import com.example.rollcall.data.model.User
import com.example.rollcall.databinding.FragmentListClassesBinding
import com.example.rollcall.ui.user.info.InfoUserFragment
import com.example.rollcall.ui.user.listclassess.classInfo.ClassInfoFragment
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.example.rollcall.utils.Utils.gotoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListClassesFragment : BaseFragment<FragmentListClassesBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_list_classes
    }

    companion object{
        fun instance(token:String?,user:User?) : ListClassesFragment {
            val bundle = Bundle().apply {
                putString(Utils.TOKEN, token)
                putSerializable(Utils.USER, user)}
            val fragment = ListClassesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel: ListClassesViewModel by viewModels()
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
                putSerializable(Utils.USER, user)
            }
            gotoFragment(requireActivity(), fragment,true)
        }
        baseBinding.apply {
            viewmodel = viewModel
            adapter = itemClassAdapter
        }

        // observe list class
        token?.let {
            user?.let { it1 ->
                viewModel.getListClass(it, it1.id, if(it1.role == Utils.TEACHER) "teachers" else "students")
            }
        }
        viewModel.classes.observe(viewLifecycleOwner, {
            it?.let {
                Log.i("class", it.data.toString())
                itemClassAdapter.submitList(it.data)

            }
        })
    }


    private fun getArgument() {
        token = arguments?.getString(Utils.TOKEN)
        user = arguments?.getSerializable(Utils.USER) as User?
        Log.d("token", token + " " + user.toString())
    }

    private fun clickView() {
    }

}