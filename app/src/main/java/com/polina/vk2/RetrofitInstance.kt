package com.polina.vk2
import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//live_ssyALQBcQkji6KfNgOluG0zk4o8vEKWKsKQrvE8NHluVE5rd2yMBWM5XlqbY4d9H
class RetrofitInstance {

    val URL = "https://api.thecatapi.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api: ImageApi by lazy {
        retrofit.create(ImageApi::class.java)
    }
}