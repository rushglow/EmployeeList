package com.example.employeelist.models

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.employeelist.databinding.AddEmployeeDialogBinding

class AddDialogFragment: AppCompatDialogFragment() {

    companion object {
        fun newInstance(employee: EmployeeClass) = AddDialogFragment().apply { //TODO: проблема была вот тут
            arguments = Bundle().apply {
                putParcelable("EMPLOYEE", employee)
            }
        }
    }

    private var _binding: AddEmployeeDialogBinding? = null // TODO: во фрагментах гугл советуею юзать вот такой подход
    private val binding get() = _binding!!

    lateinit var data: EmployeeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = arguments?.getParcelable("EMPLOYEE") ?: throw IllegalArgumentException("Data argument required") // TODO: перехват данных по ключу.
        // TODO: IllegalArgumentException тут нужен больше для тестирования, например если разные типы данных по одному ключу передаете
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
        binding.dialogNameEt.setText(data.name)
        binding.dialogPositionEt.setText(data.position)
        binding.dialogAgeEt.setText(data.age)

        binding.dialogBtnExit.setOnClickListener {
            dismiss()
        }

        binding.dialogBtnAdd.setOnClickListener {
            parentFragmentManager.setFragmentResult("KEY1", bundleOf("KEY2" to data)) // TODO: возвращаю сотрудника обраьно в активити
            dismiss()
        }
    }

}
