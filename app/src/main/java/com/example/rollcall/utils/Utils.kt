package com.example.rollcall.utils


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout


object Utils {
    const val TAG = "quocbao"
    const val USER = "user"
    var TOKEN = "token"
    const val STUDENT = "student"
    const val TEACHER = "teacher"
    const val CLASS = "class"
    const val REPORT = "report"
    const val CREATESUCCESS = "Tạo thành công"
    const val ERROREMPTY = "không được phép rỗng!"
    const val BASE_URL = "https://tncnhan.codes/api/reports/"

    fun gotoFragment(activity: FragmentActivity, fragment: Fragment, isAnim: Boolean = true) {
        activity.supportFragmentManager.beginTransaction().apply {
            if (isAnim)
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    android.R.anim.fade_in,
                    android.R.anim.slide_out_right
                )
        }
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun checkNull(editText: TextInputLayout): Boolean {
        return if (editText.editText?.text.toString() == "") {
            editText.error = ERROREMPTY
            false
        } else
            true
    }

    fun diaLogBottom(
        context: Context,
        layoutInflater: LayoutInflater,
        data: MutableList<User>,
        adapterTeacher: ItemUserAdapter? = null,
        adapterStudent: SelectItemUserAdapter? = null,
        idClass: String? = null,
    ): BottomSheetDialog {
        val sheetDialog = BottomSheetDialog(context, R.style.SheetDialog)
        val viewDialog: View = layoutInflater.inflate(R.layout.user_dialog, null)
        val textLayout = viewDialog.findViewById<TextInputLayout>(R.id.edtSearch)
        viewDialog.findViewById<RecyclerView>(R.id.recyclerViewUserDialog).apply {
            adapter = adapterStudent ?: adapterTeacher
            layoutManager = GridLayoutManager(context, 1)
        }
        textLayout.apply {
            editText?.doOnTextChanged { inputText, _, _, _ ->
                adapterStudent?.filter(inputText, data)
                adapterTeacher?.filter(inputText, data)
            }
            idClass?.let {
                editText?.setText(it)
            }
        }
        adapterTeacher?.submitList(data)

        sheetDialog.setContentView(viewDialog)
        return sheetDialog
    }

    fun showAlertDialog(context: Context, title: String, messagge: String, action: () -> Unit) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(messagge)
            .setNegativeButton("Hủy bỏ") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Đồng ý") { dialog, _ ->
                action()
                dialog.dismiss()
            }
            .show()
    }

    fun fingerPrint(
        context: FragmentActivity,
        actionFailed: (String) -> Unit,
        actionSuccess: () -> Unit,
    ) {
        val executor = ContextCompat.getMainExecutor(context)
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
            .setTitle("Xác thực Ứng dụng")
            .setDeviceCredentialAllowed(true)
            .build()
        if (isBioMetricSupported(context)) {
            biometricPrompt.authenticate(promptInfo)
        } else {
            Toast.makeText(context, "Thiết bị không được hỗ trợ vân tay", Toast.LENGTH_LONG).show()
        }

    }

    private fun isBioMetricSupported(context: FragmentActivity): Boolean {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate()) {
            // success
            BiometricManager.BIOMETRIC_SUCCESS -> {
                true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                false
            }
            else -> false
        }
    }

}