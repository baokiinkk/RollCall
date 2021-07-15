package com.example.rollcall.ui.teacher.CheckQR

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.example.rollcall.R
import com.example.rollcall.databinding.FragmentCheckinQrcodeBinding
import com.example.rollcall.utils.BaseFragment
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import me.dm7.barcodescanner.zxing.ZXingScannerView


class CheckinQRCodeFragment : BaseFragment<FragmentCheckinQrcodeBinding>(), ZXingScannerView.ResultHandler {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_checkin_qrcode
    }
    //-------------------------------- Variable ----------------------------------------
    val viewModel by viewModels<CheckinQRCodeViewModel>()

    //-------------------------------- createView ----------------------------------------
    override fun onCreateViews() {
        checkPermission()
        setup()
    }

    //-------------------------------- Func ----------------------------------------
    private fun setup() {
        baseBinding.apply {
//            viewmodel = viewModel
        }
    }

    private fun checkPermission() {
        Dexter.withContext(this.requireContext()).withPermission(Manifest.permission.CAMERA)
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
}