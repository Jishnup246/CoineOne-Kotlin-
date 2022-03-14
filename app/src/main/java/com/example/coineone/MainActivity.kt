package com.example.coineone


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import androidx.constraintlayout.widget.ConstraintLayout



class MainActivity : AppCompatActivity() {

    var textView_classTime: TextView? = null
    var textView_studyTime:TextView? = null
    var textView_freeTime:TextView? = null
    var textView_freeTime_usage:TextView? = null
    var textView_freeTime_max_usage:TextView? = null
    var textView_phone_totaltime: TextView? = null
    var textView_laptop_totaltime:TextView? = null
    var textView_total_time:TextView? = null
    var progressbar: ProgressBar? = null
    var loadprogressbar :ProgressBar? = null

    var loadscreenlayout :ConstraintLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ui_init()
        fetchData()


    }

//intialise all ui view components
    fun ui_init() {
        textView_classTime = findViewById(R.id.text_classTime)
        textView_studyTime = findViewById(R.id.text_studyTime)
        textView_freeTime = findViewById(R.id.text_freeTime)
        textView_freeTime_usage = findViewById(R.id.text_freeTime_usage)
        textView_freeTime_max_usage = findViewById(R.id.text_freeTime_max)
        progressbar = findViewById(R.id.progressBar)
        textView_phone_totaltime = findViewById(R.id.text_phone_time)
        textView_laptop_totaltime = findViewById(R.id.text_laptop_time)
        textView_total_time = findViewById(R.id.total_time_text)
        loadprogressbar=findViewById(R.id.loadscreenprogress)
        loadscreenlayout=findViewById(R.id.loadscreenlayout)


    }

//network call to backend and fetch data and if data received then it calls methods to display data on views
    fun fetchData() {

        val apiInterface = Api.create().gettime_data()
        apiInterface.enqueue( object : Callback<List<ScreenTimePojo>> {
            override fun onResponse(call: Call<List<ScreenTimePojo>>?, response: Response<List<ScreenTimePojo>>?) {
                loadprogressbar ?.visibility=View.GONE
                loadscreenlayout ?.visibility=View.GONE

                val screenTimedatabody = response!!.body()

                val screenTime_data = screenTimedatabody!![0] as ScreenTimePojo
//                Log.d("networkcall", "status=success")
//                Log.d("networkcall", "chartdata calsstime="+screenTime_data .chartdata.classtime.classTime_total)
//                Log.d("networkcall", "chartdata freetime="+screenTime_data .freetimemaxusage)
//                Log.d("networkcall", "device totalmobile="+screenTime_data .deviceusage.totaltime.total_mobile)


                set_data_to_Ui(screenTime_data)
                set_data_to_horizontalbar(screenTime_data)
                setProgressBar(screenTime_data)
                setDeviceUsage(screenTime_data)
            }

            override fun onFailure(call: Call<List<ScreenTimePojo>>, t: Throwable) {
                Log.d("jishnu", "failed")
            }
        })
    }

    private fun setDeviceUsage(screenTime_data: ScreenTimePojo) {
        textView_phone_totaltime ?.text = stringToFormatTimeString(screenTime_data.deviceusage.totaltime.total_mobile)
        textView_laptop_totaltime ?.text =stringToFormatTimeString(screenTime_data.deviceusage.totaltime.total_laptop)
    }

    private fun setProgressBar(screenTime_data: ScreenTimePojo) {
        val maximumvalue: Int = screenTime_data.freetimemaxusage.toInt()
        val currentvalue: Float = screenTime_data.chartdata.freetime.freeTime_total.toFloat()
        progressbar ?.max = maximumvalue
        val anim = ProgressBarAnimation(progressbar as ProgressBar, 0f, currentvalue)
        anim.setDuration(1000)
        progressbar ?.startAnimation(anim)
    }

    private fun set_data_to_horizontalbar(screenTime_data: ScreenTimePojo) {
        val class_time_in_HM: String
        val study_time_in_HM: String
        val free_time_in_HM: String
        val free_time_Max_in_HM: String

        class_time_in_HM =
            stringToFormatTimeString(screenTime_data.chartdata.classtime.classTime_total)


        study_time_in_HM =
            stringToFormatTimeString(screenTime_data.chartdata.studytime.studyTime_total)



        free_time_in_HM =
            stringToFormatTimeString(screenTime_data.chartdata.freetime.freeTime_total)


        free_time_Max_in_HM = stringToFormatTimeString(screenTime_data.freetimemaxusage)


        textView_classTime ?.text = class_time_in_HM
        textView_studyTime ?.text = study_time_in_HM
        textView_freeTime ?.text = free_time_in_HM
        textView_freeTime_usage ?.text = free_time_in_HM
        textView_freeTime_max_usage ?.text = free_time_Max_in_HM

    }

    private fun set_data_to_Ui(screentimeData: ScreenTimePojo) {
        val studytime= screentimeData.chartdata.studytime.studyTime_total.toInt()
        val classtime: Int = screentimeData .chartdata.classtime.classTime_total.toInt()
        val freetime: Int = screentimeData .chartdata.freetime.freeTime_total.toInt()
        val circularProgressBar = findViewById<View>(R.id.circularProgress) as CircularProgressBar
        circularProgressBar.setProgressValue(studytime, classtime, freetime)

        textView_total_time ?.text=stringToFormatTimeString(screentimeData.chartdata.totaltime.chartData_total)
    }
//this method convert minutes data to proper hour minuets format
    private fun stringToFormatTimeString(time: String): String {

        val formstedTime: String
        val timeHour: Int = time.toInt() / 60
        val timeMinute: Int = time.toInt() % 60

        formstedTime = if (timeHour == 0) {
            timeMinute.toString() + "m"
        } else if (timeMinute == 0) {
            timeHour.toString() + "h"
        } else {
            timeHour.toString() + "h " + timeMinute + "m"
        }


        return formstedTime
    }

}