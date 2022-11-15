package com.example.employeelist.models

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.employeelist.R
import com.example.employeelist.databinding.AddEmployeeDialogBinding

class AddDialogFragment: DialogFragment() {

    lateinit var binding: AddEmployeeDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var rootView:View = inflater.inflate(R.layout.add_employee_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = AddEmployeeDialogBinding.inflate(layoutInflater)

        binding.dialogBtnExit.setOnClickListener{
            dismiss()
        }

        binding.dialogBtnAdd.setOnClickListener{

        }

        return rootView
    }

    fun openDialog(position: Int){
        binding.dialogNameTv.text =
    }

}