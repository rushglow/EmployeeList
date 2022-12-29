package com.example.employeelist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.employeelist.databinding.ActivityMainBinding
import com.example.employeelist.models.AddDialogFragment
import com.example.employeelist.models.EmployeeClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class MainActivity: AppCompatActivity(), EmployeeItemAdapter.Listener {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: EmployeeItemAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var list: ArrayList<EmployeeClass>
    var employeeStatus: EmployeeClass? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initial()

        intent.extras?.getString("EMPLOYEE")

        supportFragmentManager.setFragmentResultListener("KEY1", this, this::processDialogResult) // TODO: ставлю слушетеля событий из диалога

        binding.btnAddEmployee.setOnClickListener {
                if (list.isNotEmpty()) {
                    AddDialogFragment.newInstance(null, list[list.size - 1].id + 1).show(supportFragmentManager, AddDialogFragment::class.java.simpleName)
                } else {
                    AddDialogFragment.newInstance(null,0).show(supportFragmentManager, AddDialogFragment::class.java.simpleName)
                } // TODO: можно запустить так
                employeeStatus = null
            }

        var checkId = 0
        var checkName = 0
        var checkPos = 0
        var checkAge = 0

        binding.sortId.setOnClickListener{
            checkId = if(checkId == 0){
                list.sortBy { it.id }
                adapter.setChange(list)
                1
            }else{
                list.sortByDescending { it.id }
                adapter.setChange(list)
                0
            }
        }
        binding.sortName.setOnClickListener{
            checkName = if(checkName == 0){
                list.sortBy { it.name }
                adapter.setChange(list)
                1
            }else{
                list.sortByDescending { it.name }
                adapter.setChange(list)
                0
            }
        }
        binding.sortPosition.setOnClickListener{
            checkPos = if(checkPos == 0){
                list.sortBy { it.position }
                adapter.setChange(list)
                1
            }else{
                list.sortByDescending { it.position }
                adapter.setChange(list)
                0
            }
        }
        binding.sortAge.setOnClickListener{
            checkAge = if(checkAge == 0){
                list.sortBy { it.age }
                adapter.setChange(list)
                1
            }else{
                list.sortByDescending { it.age }
                adapter.setChange(list)
                0
            }
        }


    }


    private fun initial() {
        recyclerView = binding.employeeRecycle
        adapter = EmployeeItemAdapter(this::onClickEmployee)
        recyclerView.adapter = adapter
        list = generateEmployee()
        adapter.setChange(list)
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
        val path = "${this.applicationContext.cacheDir}/employee.json"
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
        val inputFile = File("${this.applicationContext.cacheDir}/employee.json")
        if (!inputFile.exists()){
            inputFile.createNewFile()
        }
        inputFile.writeText(res, Charsets.UTF_8)
    }


    override fun onClick(employee: EmployeeClass) {
        var dialog = AddDialogFragment()
    }

    private fun onClickEmployee(employee: EmployeeClass) { //TODO: передаю этот метод в адаптер, когда из адаптера дергаю invoke, вызовется этот метод и откроет диалог
        AddDialogFragment.newInstance(employee, newEmployeeBoolean = false).show(supportFragmentManager, AddDialogFragment::class.java.simpleName)
        employeeStatus = employee
    }

    private fun processDialogResult(requestKey: String, data: Bundle) {
        val employee = data.getParcelable<EmployeeClass>("KEY2") // TODO: получаю новое значение из дилога
        //val inputFile = File("${this.applicationContext.cacheDir}/employee.json")
        //val typeToken = object: TypeToken<ArrayList<EmployeeClass>>() {}.type
       // val employeers = Gson().toJson(adapter, typeToken)

        var employeeIndex = list.indexOf(employeeStatus)
        if (employeeIndex == -1){
            list.add(employee!!)
            adapter.setChange(list)
        }else{
            list[employeeIndex] = employee!!
            adapter.setChange(list)
        }
    }

}

