package com.example.employeelist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.employeelist.databinding.EmployeeItemBinding
import com.example.employeelist.models.AddDialogFragment
import com.example.employeelist.models.EmployeeClass


//open class EmployeeItemAdapter(private val employeeList: ArrayList<EmployeeClass>, private val listener: Listener): RecyclerView.Adapter<EmployeeItemAdapter.EmployeeItemViewHolder>() {
open class EmployeeItemAdapter(private val onClick: (EmployeeClass) -> Unit): RecyclerView.Adapter<EmployeeItemAdapter.EmployeeItemViewHolder>() {

    private val items = mutableListOf<EmployeeClass>()

    class EmployeeItemViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = EmployeeItemBinding.bind(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent, false)
        return EmployeeItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeItemViewHolder, position: Int) {
        val fragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
        holder.binding.tvEmployeeName.text = items[position].name
        holder.binding.tvEmployeePosition.text = items[position].position
        holder.binding.tvEmployeeAge.text = items[position].age
        holder.binding.tvEmployeePos.text = "Должность:"
        holder.binding.tvEmployeeAg.text = "Возраст:"
        Glide.with(holder.itemView.context)
            .load(items[position].img)
            .into(holder.binding.ivEmployeeAvatar)
        holder.itemView.setOnClickListener {
            onClick.invoke(items[position]) // TODO: дергаю этот метод, чтобы он вызвался в MainActivity
//            val employee = EmployeeClass(employeeList[position].id,employeeList[position].name,employeeList[position].position,employeeList[position].age)
//            AddDialogFragment.newInstance(employee).show(fragmentManager, AddDialogFragment::class.java.simpleName)
        }

    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setChange(employee: ArrayList<EmployeeClass>) {
        items.clear()
        items.addAll(employee)
        notifyDataSetChanged()
    }

    fun replaceEmployee(index: Int, employee: EmployeeClass) {
        items.set(index, employee)
        notifyDataSetChanged()
    }

    fun addEmployee(employee: EmployeeClass) {
        items.add(employee)
        notifyDataSetChanged()
    }

    interface Listener {
        fun onClick(employee: EmployeeClass)
    }


}