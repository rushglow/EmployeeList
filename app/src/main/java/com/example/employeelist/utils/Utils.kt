package com.example.employeelist.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.employeelist.models.AddDialogFragment
import com.example.employeelist.models.EmployeeClass

fun AppCompatActivity.showDialogFragment(dialogFragment: DialogFragment) {
    dialogFragment.show(supportFragmentManager, dialogFragment::class.java.simpleName)
}

