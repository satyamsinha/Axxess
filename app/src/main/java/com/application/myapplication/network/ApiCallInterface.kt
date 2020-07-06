package com.application.myapplication.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiCallInterface {


    //@Headers("Authorization:Client-ID 137cda6b5008a7c")
    @Headers("Content-Type:application/json","Authorization:Client-ID 137cda6b5008a7c")
    @GET("1?q=vanilla")
    fun  getItemsDetails(): Call<ResponseData>
}