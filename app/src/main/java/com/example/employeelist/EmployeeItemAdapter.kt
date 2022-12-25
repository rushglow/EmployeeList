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


//open class EmployeeItemAdapter(private val employeeList: ArrayList<EmployeeClass>, private val listener: Listener): RecyclerView.Adapter<EmployeeItemAdapter.EmployeeItemViewHolder>() {
open class EmployeeItemAdapter(private val employeeList: ArrayList<EmployeeClass>, private val onClick: (EmployeeClass) -> Unit): RecyclerView.Adapter<EmployeeItemAdapter.EmployeeItemViewHolder>() {

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
            onClick.invoke(employeeList[position]) // TODO: дергаю этот метод, чтобы он вызвался в MainActivity
//            val employee = EmployeeClass(employeeList[position].id,employeeList[position].name,employeeList[position].position,employeeList[position].age)
//            AddDialogFragment.newInstance(employee).show(fragmentManager, AddDialogFragment::class.java.simpleName)
        }

    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setChange (employee: ArrayList<EmployeeClass>) {
        notifyDataSetChanged()
    }

    fun replaceEmployee(index:Int, employee: EmployeeClass){
        employeeList.set(index, employee)
        notifyDataSetChanged()
    }

    fun addEmployee(employee: EmployeeClass) {
        employeeList.add(employee)
        notifyDataSetChanged()
    }

    fun sortById(employee: ArrayList<EmployeeClass>, check: Int){
        if(check == 0){
            employeeList.sortBy { it.id }
        }else{
            employeeList.sortedByDescending{ it.id }
        }
        notifyDataSetChanged()
    }

    fun sortByName(employee: ArrayList<EmployeeClass>, check: Int){
        if(check == 0){
            employeeList.sortBy { it.name }
        }else{
            employeeList.sortedByDescending{ it.name }
        }
        notifyDataSetChanged()
    }

    fun sortByPosition(employee: ArrayList<EmployeeClass>, check: Int){
        if(check == 0){
            employeeList.sortBy { it.position }
        }else{
            employeeList.sortedByDescending{ it.position }
        }
        notifyDataSetChanged()
    }

    fun sortByAge(employee: ArrayList<EmployeeClass>, check: Int){
        if(check == 0){
            employeeList.sortBy { it.age }
        }else{
            employeeList.sortedByDescending{ it.age }
        }
        notifyDataSetChanged()
    }



    interface  Listener{
        fun onClick(employee: EmployeeClass)
    }


}