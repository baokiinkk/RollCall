package com.example.rollcall.utils


import android.app.Activity
import android.content.Context
import android.hardware.biometrics.BiometricManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricManager.from
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rollcall.R
import com.example.rollcall.adapter.ItemUserAdapter
import com.example.rollcall.adapter.SelectItemUserAdapter
import com.example.rollcall.data.model.User
import com.example.rollcall.ui.admin.home.HomeAdminFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout


object Utils {
    const val TAG = "quocbao"


    const val SNS_RESULT_DATA = "SNS_RESULT_DATA"

    const val SNS_LOGIN_TYPE = "SNS_LOGIN_TYPE"
    const val USER = "user"
    var TOKEN = "token"
    const val STUDENT = "student"
    const val TEACHER = "teacher"
    const val CLASS = "class"
    const val SNS_REQUEST_CODE_GOOGLE = 887

    const val SNS_REQUEST_CODE_PHONE = 886

    fun gotoFragment(activity: FragmentActivity, fragment: Fragment) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(HomeAdminFragment::class.java.simpleName)
            .commit()
    }

    fun checkNull(editText: TextInputLayout): Boolean {
        if (editText.editText?.text.toString() == "") {
            editText.error = " không được phép rỗng!"
            return false
        } else
            return true
    }

    fun diaLogBottom(
        context: Context,
        layoutInflater: LayoutInflater,
        data: MutableList<User>,
        adapterTeacher: ItemUserAdapter? = null,
        adapterStudent: SelectItemUserAdapter? = null
    ): BottomSheetDialog {
        val sheetDialog = BottomSheetDialog(context, R.style.SheetDialog)
        val viewDialog: View = layoutInflater.inflate(R.layout.user_dialog, null)

        viewDialog.findViewById<RecyclerView>(R.id.recyclerViewUserDialog).apply {
            adapter = adapterStudent ?: adapterTeacher
            layoutManager = GridLayoutManager(context, 1)
        }
        (adapterStudent ?: adapterTeacher)?.submitList(data)
        sheetDialog.setContentView(viewDialog)
        return sheetDialog
    }

    fun fingerPrint(
        context: FragmentActivity,
        actionFailed: (String) -> Unit,
        actionSuccess: () -> Unit
    ) {
        var executor = ContextCompat.getMainExecutor(context)
        val biometricPrompt =
            BiometricPrompt(context, executor, object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    actionFailed("Authentication error: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    actionSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    actionFailed("Authentication failed")
                }
            })
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun verfiyingBioMetricExistence(context: Activity) {
//        val biometricManager = BiometricManager.from(context)
//        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
//            BiometricManager.BIOMETRIC_SUCCESS ->
//                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
//            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
//                Log.e("MY_APP_TAG", "No biometric features available on this device.")
//            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
//                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
//        }
    }

}