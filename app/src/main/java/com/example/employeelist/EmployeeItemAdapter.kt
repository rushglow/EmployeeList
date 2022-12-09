package com.example.employeelist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.employeelist.databinding.EmployeeItemBinding
import com.example.employeelist.models.AddDialogFragment
import com.example.employeelist.models.EmployeeClass


open class EmployeeItemAdapter(private val employeeList: ArrayList<EmployeeClass>, val listener: Listener): RecyclerView.Adapter<EmployeeItemAdapter.EmployeeItemViewHolder>() {

    class EmployeeItemViewHolder(item : View):RecyclerView.ViewHolder(item ){
        val binding = EmployeeItemBinding.bind(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent, false)
        return EmployeeItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeItemViewHolder, position: Int) {
        val fragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
        holder.binding.tvEmployeeName.text = employeeList[position].name
        holder.binding.tvEmployeePosition.text = employeeList[position].position
        holder.binding.tvEmployeeAge.text = employeeList[position].age
        holder.binding.tvEmployeePos.text = "Должность:"
        holder.binding.tvEmployeeAg.text = "Возраст:"
        holder.itemView.setOnClickListener{
            listener.onClick(employeeList[position])
            val employee = EmployeeClass(employeeList[position].id,employeeList[position].name,employeeList[position].position,employeeList[position].age)
            val intent = Intent(holder.itemView.context, AddDialogFragment::class.java)
            val bundle = Bundle()
            bundle.putParcelable("EMPLOYEE", employee)
            AddDialogFragment.newInstance(employee).show(fragmentManager, AddDialogFragment.newInstance(employee)::class.java.simpleName)
        }

    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setChange (list: List<EmployeeClass>) {
        notifyDataSetChanged()
    }

    fun addEmployee(employee: EmployeeClass) {
        employeeList.add(employee)
        notifyDataSetChanged()
    }

    interface  Listener{
        fun onClick(employee: EmployeeClass)
    }


}