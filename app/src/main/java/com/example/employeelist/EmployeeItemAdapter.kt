package com.example.employeelist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.employeelist.databinding.EmployeeItemBinding
import com.example.employeelist.models.EmployeeClass
import com.google.gson.Gson
import java.net.URI

class EmployeeItemAdapter(private val employeeList: List<EmployeeClass>): RecyclerView.Adapter<EmployeeItemAdapter.EmployeeItemViewHolder>() {


    class EmployeeItemViewHolder(item : View):RecyclerView.ViewHolder(item ){
        val binding = EmployeeItemBinding.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent, false)
        return EmployeeItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeItemViewHolder, position: Int) {
        holder.binding.tvEmployeeName.text = employeeList[position].name
        holder.binding.tvEmployeePosition.text = employeeList[position].position
        holder.binding.tvEmployeeAge.text = employeeList[position].age
        holder.binding.tvEmployeePos.text = "Должность:"
        holder.binding.tvEmployeeAg.text = "Возраст:"
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setChange (list: List<EmployeeClass>) {
        notifyDataSetChanged()
    }

}