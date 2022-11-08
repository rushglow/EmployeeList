package com.example.employeelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeelist.databinding.ActivityMainBinding
import com.example.employeelist.models.EmployeeClass
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: EmployeeItemAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initial()
    }

    private fun initial() {
        recyclerView = binding.employeeRecycle
        adapter = EmployeeItemAdapter(generateEmployee())
        recyclerView.adapter = adapter
        adapter.setChange(generateEmployee())
    }

    fun generateEmployee(): List<EmployeeClass> {
        return listOf(
            EmployeeClass(1, "Vasiliy Pupkin", "Prikoler"),
            EmployeeClass(2, "Alexey Chehov", "Joker")
        )
    }


}