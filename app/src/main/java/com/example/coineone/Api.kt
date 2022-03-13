package com.example.coineone
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Api {



    @GET("screentime")
    fun gettime_data(): Call<List<ScreenTimePojo>>

    companion object {

        var BASE_URL = "https://api.mocklets.com/mock68182/"

        fun create() : Api{

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(Api::class.java)

        }
    }

}