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
                    AddDialogFragment.newInstance(list[list.size - 1].id + 1)
                } else {
                    AddDialogFragment.newInstance(0)
                }
                AddDialogFragment.newInstance(null).show(
                    supportFragmentManager,
                    AddDialogFragment.newInstance(null)::class.java.simpleName
                ) // TODO: можно запустить так
                employeeStatus = null
            }

        var checkId = 0
        var checkName = 0
        var checkPos = 0
        var checkAge = 0

        binding.sortId.setOnClickListener{
            if(checkId == 0){
                adapter.sortById(list, checkId)
                checkId = 1
            }else{
                adapter.sortById(list, checkId)
                checkId = 0
            }
        }
        binding.sortName.setOnClickListener{
            if(checkName == 0){
                adapter.sortByName(list, checkName)
                checkName = 1
            }else{
                adapter.sortByName(list, checkName)
                checkName = 0
            }
        }
        binding.sortPosition.setOnClickListener{
            if(checkPos == 0){
                adapter.sortByPosition(list, checkPos)
                checkPos = 1
            }else{
                adapter.sortByPosition(list, checkPos)
                checkPos = 0
            }
        }
        binding.sortAge.setOnClickListener{
            if(checkAge == 0){
                adapter.sortByAge(list, checkAge)
                checkAge = 1
            }else{
                adapter.sortByAge(list, checkAge)
                checkAge = 0
            }
        }


    }


    private fun initial() {
        recyclerView = binding.employeeRecycle
        adapter = EmployeeItemAdapter(generateEmployee(), this::onClickEmployee)
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
        AddDialogFragment.newInstance(employee).show(supportFragmentManager, AddDialogFragment::class.java.simpleName)
        employeeStatus = employee
    }

    private fun processDialogResult(requestKey: String, data: Bundle) {
        val employee = data.getParcelable<EmployeeClass>("KEY2") // TODO: получаю новое значение из дилога
        //val inputFile = File("${this.applicationContext.cacheDir}/employee.json")
        //val typeToken = object: TypeToken<ArrayList<EmployeeClass>>() {}.type
       // val employeers = Gson().toJson(adapter, typeToken)

        var employeeIndex = generateEmployee().indexOf(employeeStatus)
        if (employeeIndex == -1){
            adapter.addEmployee(employee!!)
            //inputFile.writeText(employeers)
        }else{
            adapter.replaceEmployee(employeeIndex, employee!!)
        }
    }

}

