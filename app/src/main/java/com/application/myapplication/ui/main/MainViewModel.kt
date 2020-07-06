package com.application.myapplication.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.application.myapplication.network.ApiCallInterface
import com.application.myapplication.network.ResponseData
import com.application.myapplication.network.RetrofitInstance
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

class MainViewModel (application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    //private var application: Context
    var liveDataString = MutableLiveData<ResponseData>()

    init {
        var application : Context =application
    }
    fun getAllData (): MutableLiveData <ResponseData> {
        GlobalScope.launch {
            getDataFromResource()
        }
        return liveDataString
    }

    private fun getDataFromResource() {
        try {
            val request =
                RetrofitInstance.getRetrofitInstance().create(ApiCallInterface::class.java)
            request.getItemsDetails().enqueue(object : Callback<ResponseData> {

                override fun onResponse(call: Call<ResponseData>,response: retrofit2.Response<ResponseData>) {
                    val resp: String = response.body().toString()
                    liveDataString.value = response.body()
                }
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {

                }
            })
        }
        catch (e:Exception){

           // return

        }
    }
}