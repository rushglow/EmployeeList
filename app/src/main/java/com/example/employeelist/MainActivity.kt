package com.example.employeelist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.employeelist.databinding.ActivityMainBinding
import com.example.employeelist.models.AddDialogFragment
import com.example.employeelist.models.EmployeeClass
import com.example.employeelist.utils.showDialogFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class MainActivity: AppCompatActivity(), EmployeeItemAdapter.Listener {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: EmployeeItemAdapter
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initial()
        intent.extras?.getString("EMPLOYEE")

        supportFragmentManager.setFragmentResultListener("KEY1", this, this::processDialogResult) // TODO: ставлю слушетеля событий из диалога

        binding.btnAddEmployee.setOnClickListener {
            //AddDialogFragment.newInstance().show(supportFragmentManager, AddDialogFragment.newInstance()::class.java.simpleName) // TODO: можно запустить так
            //showDialogFragment(AddDialogFragment.newInstance()) // TODO: а можно вот так, реализацию функции можно найти ниже (обычно мы такие вещи выносим в отдельный файл, потому что очень часто он много где используется)
        }
    }

    private fun initial() {
        recyclerView = binding.employeeRecycle
//        adapter = EmployeeItemAdapter(generateEmployee(), this)
        adapter = EmployeeItemAdapter(generateEmployee(), this::onClickEmployee)
        recyclerView.adapter = adapter
        adapter.setChange(generateEmployee())
    }

    fun generateEmployee(): ArrayList<EmployeeClass> {
        val javaString = getFileData()
        val typeToken = object: TypeToken<ArrayList<EmployeeClass>>() {}.type
        val authors = Gson().fromJson<ArrayList<EmployeeClass>>(javaString, typeToken)
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


    override fun onClick(employee: EmployeeClass) {
        var dialog = AddDialogFragment()
    }

    private fun onClickEmployee(employee: EmployeeClass) { //TODO: передаю этот метод в адаптер, когда из адаптера дергаю invoke, вызовется этот метод и откроет диалог
        AddDialogFragment.newInstance(employee).show(supportFragmentManager, AddDialogFragment::class.java.simpleName)
    }

    private fun processDialogResult(requestKey: String, data: Bundle) {
        val employee = data.getParcelable<EmployeeClass>("KEY2") // TODO: получаю новое значение из дилога
    }

    fun dataSwap() {

    }


}

