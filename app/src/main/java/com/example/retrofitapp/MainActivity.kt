package com.example.retrofitapp

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.retrofitapp.data.models.RegisterRequestBodyData
import com.example.retrofitapp.data.models.ResultData
import com.example.retrofitapp.data.models.TaskData
import com.example.retrofitapp.data.remote.RetrofitHelper
import com.example.retrofitapp.data.remote.TodoApi
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(application)
        ).get(MainViewModel::class.java)


        lifecycleScope.launchWhenResumed {
            viewModel.getAllTasks()
        }

        initObservers()


        val retrofit = RetrofitHelper.getInstance()


        val api = retrofit.create(TodoApi::class.java)


        val token = "8|Og0yXfLouXBcUzjjlyVXRRjuRSd1ebzCQnSIhNaw"
        lifecycleScope.launchWhenResumed {


            val response =
                api.registerUser(RegisterRequestBodyData("Atabek", "1324", "+998992999999"))
            if (response.isSuccessful) {
                Toast.makeText(
                    this@MainActivity,
                    "Success: ${response.body()?.payload?.token}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
//                Toast.makeText(this@MainActivity, "Error: ${}", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun initObservers() {
        viewModel.getAllTasksFlow.onEach {
            Log.d("TTTT", "Adapter.submitList(it)")
        }.launchIn(lifecycleScope)


        viewModel.messageFlow.onEach {
        }.launchIn(lifecycleScope)
        viewModel.errorFlow.onEach {

        }.launchIn(lifecycleScope)
    }
}


class MainRepository(val api: TodoApi) {
    suspend fun getAllTasks() = flow {
        val token = "asldnajksndjkasnjkdkansd"
        val response = api.getAllTasks("Bearer $token")
        if (response.isSuccessful) {
            emit(ResultData.Success(response.body()!!))
        } else {
            emit(ResultData.Message(response.message()))
        }
    }.catch {
        emit(ResultData.Error(it))
    }
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val repo = MainRepository(RetrofitHelper.getInstance().create(TodoApi::class.java))

    val getAllTasksFlow = MutableSharedFlow<List<TaskData>>()
    val messageFlow = MutableSharedFlow<String>()
    val errorFlow = MutableSharedFlow<Throwable>()

    suspend fun getAllTasks() {
        repo.getAllTasks().onEach {
        }.launchIn(viewModelScope)
    }
}