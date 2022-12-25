package com.example.employeelist.models

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.employeelist.databinding.AddEmployeeDialogBinding

class AddDialogFragment: AppCompatDialogFragment() {

    var data: EmployeeClass? = null
    var employeeId: Int? = null
    var newData: EmployeeClass? = null

    companion object {
        fun newInstance(employee: EmployeeClass?) = AddDialogFragment().apply { //TODO: проблема была вот тут
            arguments = Bundle().apply {
                putParcelable("EMPLOYEE", employee)
            }
        }
        fun newInstance(employeeId: Int) = AddDialogFragment().apply { //TODO: проблема была вот тут
            arguments = Bundle().apply {
                putInt("EMPLOYEE_ID", employeeId)
            }
        }
    }


    private var _binding: AddEmployeeDialogBinding? = null // TODO: во фрагментах гугл советуею юзать вот такой подход
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = arguments?.getParcelable("EMPLOYEE")
        employeeId = arguments?.getInt("EMPLOYEE_ID", -1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = AddEmployeeDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) { // TODO: всю работу с кнопками/данными лучше вынести сюда, когда вьюшка уже создалась
        super.onViewCreated(view, savedInstanceState)
        binding.dialogNameEt.setText(data?.name)
        binding.dialogPositionEt.setText(data?.position)
        binding.dialogAgeEt.setText(data?.age)
        binding.employeeIdTv.text = data?.id.toString()

        binding.dialogBtnExit.setOnClickListener {
            dismiss()
        }

        binding.dialogBtnAdd.setOnClickListener {
            if (employeeId == -1){
                newData = EmployeeClass(data!!.id, binding.dialogNameEt.text.toString(), binding.dialogPositionEt.text.toString(), binding.dialogAgeEt.text.toString())
                parentFragmentManager.setFragmentResult("KEY1", bundleOf("KEY2" to newData))
            }else{
                newData = EmployeeClass(employeeId!!, binding.dialogNameEt.text.toString(), binding.dialogPositionEt.text.toString(), binding.dialogAgeEt.text.toString())
                parentFragmentManager.setFragmentResult("KEY1", bundleOf("KEY2" to newData))
            }
             // TODO: возвращаю сотрудника обраьно в активити
            dismiss()
            Toast.makeText(context, "${employeeId}", Toast.LENGTH_LONG).show()
        }
    }

}
