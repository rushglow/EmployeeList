package com.example.employeelist.models

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.os.bundleOf
import com.example.employeelist.EmployeeItemAdapter
import com.example.employeelist.databinding.DeleteEmployeeConfirmDialogBinding

class DeleteDialogFragment: AppCompatDialogFragment() {
    var name:String? = null
    var data: EmployeeClass? = null
    lateinit var adapter: EmployeeItemAdapter

    companion object {
        fun newInstance(employee: EmployeeClass?) = DeleteDialogFragment().apply { //TODO: проблема была вот тут
            arguments = Bundle().apply {
                putParcelable("EMPLOYEE_DEL", employee)
            }
        }
    }

    private var _binding: DeleteEmployeeConfirmDialogBinding? = null // TODO: во фрагментах гугл советуею юзать вот такой подход
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = arguments?.getParcelable("EMPLOYEE_DEL")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DeleteEmployeeConfirmDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.deleteEmployeeConfirmTv.text = "Вы действтельно хотите удалить работника " +
                "${data?.name}?"
        binding.dialogBtnNo.setOnClickListener{
            dismiss()
        }
        binding.dialogBtnYes.setOnClickListener{
            var dataDel = data
            parentFragmentManager.setFragmentResult("DEL_DECISION", bundleOf("DEL_DECISION_ANSWER" to dataDel))
            dismiss()
        }
    }
}