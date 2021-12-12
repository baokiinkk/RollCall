package com.example.rollcall.ui.user.report

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemUserReportAdapter
import com.example.rollcall.data.model.Report
import com.example.rollcall.data.model.User
import com.example.rollcall.databinding.FragmentUsersReportBinding
import com.example.rollcall.ui.user.CheckQR.CheckinQRCodeFragment
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
    private var report: Report? = null
    private var user: User? = null
    private val itemUserReportAdapter: ItemUserReportAdapter by lazy {
        ItemUserReportAdapter()
    }

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        getArgument()
        setup()
        getData()
        clickView()
    }



    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        baseBinding.apply {
            viewmodel = viewModel
            adapter = itemUserReportAdapter
        }
        viewModel.getReport(report?.id, token)

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
        report = arguments?.getSerializable(Utils.REPORT) as Report?
        user = arguments?.getSerializable(Utils.USER) as User
    }

    private fun clickView() {
        baseBinding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        baseBinding.scanQr.setOnClickListener {
            val fragment = CheckinQRCodeFragment()
            fragment.arguments = Bundle().apply {
                putString(Utils.TOKEN, token)
                putSerializable(Utils.REPORT, report)
                putSerializable(Utils.USER, user)
            }
            Utils.gotoFragment(requireActivity(),fragment)
        }
    }
}