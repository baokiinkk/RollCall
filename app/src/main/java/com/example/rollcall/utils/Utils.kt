package com.example.rollcall.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
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
    const val CREATESUCCESS = "Tạo thành công"
    const val ERROREMPTY = "không được phép rỗng!"

    fun gotoFragment(activity: FragmentActivity, fragment: Fragment) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(HomeAdminFragment::class.java.simpleName)
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
        idClass: String? = null
    ): BottomSheetDialog {
        val sheetDialog = BottomSheetDialog(context, R.style.SheetDialog)
        val viewDialog: View = layoutInflater.inflate(R.layout.user_dialog,null)
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

    fun showAlertDialog(context: Context,title:String,messagge:String,action:()->Unit){
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

}