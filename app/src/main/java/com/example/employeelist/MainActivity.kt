package com.example.employeelist

import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.employeelist.databinding.ActivityMainBinding
import com.example.employeelist.models.AddDialogFragment
import com.example.employeelist.models.EmployeeClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: EmployeeItemAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initial()

        binding.btnAddEmployee.setOnClickListener{
            var dialog = AddDialogFragment()
            dialog.show(supportFragmentManager, "addEmployeeDialog")
        }
    }

    private fun initial() {
        recyclerView = binding.employeeRecycle
        adapter = EmployeeItemAdapter(generateEmployee())
        recyclerView.adapter = adapter
        adapter.setChange(generateEmployee())
    }

    fun generateEmployee(): List<EmployeeClass> {
        val javaString = getFileData()
        val typeToken = object : TypeToken<List<EmployeeClass>>() {}.type
        val authors = Gson().fromJson<List<EmployeeClass>>(javaString, typeToken)
        return authors
    }

    // Метод для получения списка студентов
    fun getFileData(): String {
        var result = "" // Переменная, куда записывается JSON-файл в текстовом виде
        val path = "employee.txt"
        val isFileExists = File(path).exists() // Проверка, существует ли нужный файл в cacheDir
        val inputStream = if (isFileExists) { // Если файл существует, то в переменную запишется InputStream уже файла из cacheDir
            File(path).inputStream()
        } else {
            this.assets.open("employee.json") // Если файла не существует, то в переменную запишется InputStream из файла в assets
        }
        try {
            result = inputStream.bufferedReader().use { it.readText() } // Считывание данных из входного потока (InputStream)
            if (!isFileExists) writeDataToCacheFile(result) // Если файла не существует, то сразу запишутся считанные данные из assets файла
        } catch (e: Exception) {
            e.printStackTrace() // если упали в ошибку, то они напечатаются в логах
        } finally {
            inputStream.close() // закрытие входного потока
        }

        return result
    }

    private fun writeDataToCacheFile(res: String) {
        val inputFile = File("employee.txt")
        inputFile.writeText(res, Charsets.UTF_8)
    }


}

