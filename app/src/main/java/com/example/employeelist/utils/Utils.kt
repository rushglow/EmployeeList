package com.example.employeelist.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

fun AppCompatActivity.showDialogFragment(dialogFragment: DialogFragment) {
    dialogFragment.show(supportFragmentManager, dialogFragment::class.java.simpleName)
}