package com.example.employeelist.models

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.employeelist.R
import com.example.employeelist.databinding.AddEmployeeDialogBinding

class AddDialogFragment: DialogFragment() {
    companion object {
        fun newInstance()= AddDialogFragment().apply {
            arguments = Bundle().apply {
//                putString("KEY", "smth") // TODO: передача данных извне тут. Если вы будете предавать объект, то необходимо юзать putParcelable(key: String, object: Any)
            }
        }
    }

    private var _binding: AddEmployeeDialogBinding? = null // TODO: во фрагментах гугл советуею юзать вот такой подход
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = arguments?.getString("key") ?: throw IllegalArgumentException("Data argument required") // TODO: перехват данных по ключу.
    // TODO: IllegalArgumentException тут нужен больше для тестирования, например если разные типы данных по одному ключу передаете

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = AddEmployeeDialogBinding.inflate(layoutInflater)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // TODO: всю работу с кнопками/данными лучше вынести сюда, когда вьюшка уже создалась
        super.onViewCreated(view, savedInstanceState)

        binding.dialogBtnExit.setOnClickListener{
            dismiss()
        }

        binding.dialogBtnAdd.setOnClickListener{

        }
    }

    fun openDialog(position: Int){
//        binding.dialogNameTv.text =
    }

}
