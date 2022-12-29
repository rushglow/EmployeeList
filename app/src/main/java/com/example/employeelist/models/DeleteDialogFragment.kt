package com.example.employeelist.models

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.employeelist.databinding.AddEmployeeDialogBinding
import com.example.employeelist.databinding.DeleteEmployeeConfirmDialogBinding

class DeleteDialogFragment: AppCompatDialogFragment() {
    var name:String? = null

    companion object {
        fun newInstance(name: String?) = AddDialogFragment().apply { //TODO: проблема была вот тут
            arguments = Bundle().apply {
                putString("EMPLOYEE_NAME", name)
            }
        }
    }

    private var _binding: DeleteEmployeeConfirmDialogBinding? = null // TODO: во фрагментах гугл советуею юзать вот такой подход
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments?.getString("EMPLOYEE_NAME")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DeleteEmployeeConfirmDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}