package com.example.mycurrencyapp.networking

import com.example.mycurrencyapp.model.CurrencyModel
import retrofit2.Call
import retrofit2.http.GET

interface CurrencyApi {

    @GET("arkhiv-kursov-valyut/json/")
    fun getAllCurrency(): Call<List<CurrencyModel>>

}