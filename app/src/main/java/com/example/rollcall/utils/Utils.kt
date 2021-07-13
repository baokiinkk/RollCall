package com.example.rollcall.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.rollcall.R
import com.example.rollcall.ui.admin.home.HomeAdminFragment
import com.google.android.material.textfield.TextInputEditText


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

    fun gotoFragment(activity: FragmentActivity,fragment: Fragment) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(HomeAdminFragment::class.java.simpleName)
            .commit()
    }

     fun checkNull(text: String, hint: String, editText: TextInputEditText): Boolean {
        if (text == "") {
            editText.error = hint.substring(5) + " không được phép rỗng"
            return false
        } else
            return true
    }

}