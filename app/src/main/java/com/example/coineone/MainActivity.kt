package com.example.coineone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val circularProgressBar = findViewById<View>(R.id.circularProgress) as CircularProgressBar
        circularProgressBar.setProgressValue(60, 60, 60)

        val apiInterface = Api.create().gettime_data()
        apiInterface.enqueue( object : Callback<List<ScreenTimePojo>> {
            override fun onResponse(call: Call<List<ScreenTimePojo>>?, response: Response<List<ScreenTimePojo>>?) {

                val screenTimedatabody = response!!.body()

                val screenTimedata = screenTimedatabody!![0] as ScreenTimePojo
                Log.d("networkcall", "status=success")
                Log.d("networkcall", "chartdata calsstime="+screenTimedata ?.chartdata.classtime.classTime_total)
                Log.d("networkcall", "chartdata freetime="+screenTimedata ?.freetimemaxusage)
                Log.d("networkcall", "device totalmobile="+screenTimedata ?.deviceusage.totaltime.total_mobile)

            }

            override fun onFailure(call: Call<List<ScreenTimePojo>>?, t: Throwable?) {

            }
        })

    }
}