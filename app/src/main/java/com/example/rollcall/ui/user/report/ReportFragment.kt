package com.example.rollcall.ui.user.report

import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemUserReportAdapter
import com.example.rollcall.databinding.FragmentUsersReportBinding
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentUsersReportBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_users_report
    }

    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<ReportViewModel>()
    private var token: String? = null
    private var idClass: String? = null
    private val itemUserReportAdapter: ItemUserReportAdapter by lazy {
        ItemUserReportAdapter()
    }

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        getArgument()
        setup()
        getData()
    }

    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        baseBinding.apply {
            viewmodel = viewModel
            adapter = itemUserReportAdapter
        }
        viewModel.getReport(idClass, token)

    }

    private fun getData() {

        viewModel.users.observe(viewLifecycleOwner,{
            it?.let {
                itemUserReportAdapter.submitList(it.data[0].content)
            }
        })
    }

    private fun getArgument() {
        token = arguments?.getString(Utils.TOKEN)
        idClass = arguments?.getString(Utils.CLASS)
    }


}