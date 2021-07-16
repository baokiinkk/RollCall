package com.example.rollcall.ui.user.CheckQR

import android.Manifest
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.databinding.FragmentCheckinQrcodeBinding
import com.example.rollcall.utils.BaseFragment
import com.example.rollcall.utils.Utils
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import me.dm7.barcodescanner.zxing.ZXingScannerView

@AndroidEntryPoint
class CheckinQRCodeFragment : BaseFragment<FragmentCheckinQrcodeBinding>(), ZXingScannerView.ResultHandler, View.OnClickListener {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_checkin_qrcode
    }
    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<CheckinQRCodeViewModel>()

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        checkPermission()
        setup()
        setEvent()
    }

    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        baseBinding.apply {
//            viewmodel = viewModel
        }
    }

    private fun checkPermission() {
        Dexter.withActivity(requireActivity()).withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    baseBinding.scanQr.resumeCameraPreview(this@CheckinQRCodeFragment)
                    baseBinding.scanQr.startCamera()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    Toast.makeText(requireContext(),
                        "You must enable this permission",
                        Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?,
                ) {
                }
            }).check()
    }

    private fun setEvent() {
        baseBinding.tvResult.setOnClickListener(this)
    }

    private fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val translateAnimation = TranslateAnimation(0.0f, 0.0f,
            view.height.toFloat(), 0.0f)
        translateAnimation.duration = 300
        translateAnimation.fillAfter = true
        view.startAnimation(translateAnimation)
    }

    private fun slideDown(view: View) {
        val translateAnimation = TranslateAnimation(0.0f, 0.0f, 0.0f,
            view.height.toFloat())
        translateAnimation.duration = 300
        translateAnimation.fillAfter = true
        view.startAnimation(translateAnimation)
        view.visibility = View.GONE
    }

    override fun handleResult(rawResult: Result?) {
        val scanResult = rawResult!!.text
        baseBinding.tvResult.text = scanResult
        slideUp(baseBinding.layoutBottomCheckinResult)
    }

    override fun onResume() {
        super.onResume()
        baseBinding.scanQr.resumeCameraPreview(this@CheckinQRCodeFragment)
        baseBinding.scanQr.startCamera()
    }

    override fun onPause() {
        super.onPause()
        baseBinding.scanQr.stopCamera()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_result -> {
                Utils.fingerPrint(requireActivity(), {
                    Toast.makeText(requireContext(), "fail!", Toast.LENGTH_SHORT).show()
                }) {

                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                }
                slideDown(baseBinding.layoutBottomCheckinResult)
            }
        }
    }
}