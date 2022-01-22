package com.example.mycurrencyapp.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyRetrofit {

    companion object{
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(logging).build()

            Retrofit.Builder()
                .baseUrl("https://cbu.uz/uz/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api: CurrencyApi by lazy {
            retrofit.create(CurrencyApi::class.java)
        }
    }

}