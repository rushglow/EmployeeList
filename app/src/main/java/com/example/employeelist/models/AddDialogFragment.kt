package com.example.employeelist.models

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.app.BundleCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.example.employeelist.databinding.AddEmployeeDialogBinding

class AddDialogFragment: AppCompatDialogFragment() {

    var data: EmployeeClass? = null
    var employeeId: Int? = null
    var newData: EmployeeClass? = null
    var newEmployeeBoolean: Boolean? = null

    companion object {
        fun newInstance(employee: EmployeeClass?, employeeId: Int = -1, newEmployeeBoolean: Boolean = true) = AddDialogFragment().apply { //TODO: проблема была вот тут
            arguments = Bundle().apply {
                putParcelable("EMPLOYEE", employee)
                putInt("EMPLOYEE_ID", employeeId)
                putBoolean("NEW_EMPLOYEE", newEmployeeBoolean)
            }
        }
    }


    private var _binding: AddEmployeeDialogBinding? = null // TODO: во фрагментах гугл советуею юзать вот такой подход
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = arguments?.getParcelable("EMPLOYEE")
        employeeId = arguments?.getInt("EMPLOYEE_ID")
        newEmployeeBoolean = arguments?.getBoolean("NEW_EMPLOYEE")
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

        if (newEmployeeBoolean == false){
            binding.dialogBtnDelete.visibility = View.VISIBLE
        }
        binding.dialogBtnDelete.setOnClickListener{
            DeleteDialogFragment.newInstance(data).show(childFragmentManager, DeleteDialogFragment :: class.java.name)
        }

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
        }
        childFragmentManager.setFragmentResultListener("DEL_DECISION", this, this::deleteDialogResult)
    }

    fun deleteDialogResult(requestKey: String, data: Bundle){
        val newData = data.getParcelable<EmployeeClass>("DEL_DECISION_ANSWER")
        parentFragmentManager.setFragmentResult("DEL_DECISION", bundleOf("DEL_DECISION_ANSWER" to newData))
        dismiss()
    }
}
