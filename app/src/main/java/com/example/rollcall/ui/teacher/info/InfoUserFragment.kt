package com.example.rollcall.ui.teacher.info

import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.data.model.User
import com.example.rollcall.databinding.FragmentInfoUserBinding
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoUserFragment : BaseFragment<FragmentInfoUserBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home_teacher
    }
    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<InfoUserViewModel>()
    private var token: String? = null
    private var user: User? = null

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        getArgument()
        getData()
        setup()
    }

    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        baseBinding.apply {
            viewmodel = viewModel
        }
    }

    private fun getData() {
        viewModel.getInfoUser(token,user?.id)
    }

    private fun getArgument() {
        token = arguments?.getString(Utils.TOKEN)
        user = arguments?.getSerializable(Utils.USER) as User?
    }


}