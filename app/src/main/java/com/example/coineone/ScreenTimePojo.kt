package com.example.coineone
import com.google.gson.annotations.SerializedName
data class ScreenTimePojo(@SerializedName("chartData") val chartdata : chartData,
                          @SerializedName("deviceUsage") val deviceusage :deviceUsage,
                          @SerializedName("freeTimeMaxUsage") val freetimemaxusage :String)

{

    data class chartData(@SerializedName("totalTime") val totaltime:totalTime,
                         @SerializedName("studyTime") val studytime : studyTime,
                         @SerializedName("classTime") val classtime : classTime,
                         @SerializedName("freeTime") val freetime : freeTime )
    {

        data class totalTime(@SerializedName("total")val chartData_total: String )
        data class studyTime(@SerializedName("total")val studyTime_total: String )
        data class classTime(@SerializedName("total")val classTime_total: String )
        data class freeTime(@SerializedName("total")val freeTime_total: String )




    }

    data class deviceUsage(@SerializedName("totalTime") val totaltime: totalTime,
                           @SerializedName("studyTime") val studytime : studyTime,
                           @SerializedName("classTime") val classtime : classTime,
                           @SerializedName("freeTime") val freetime : freeTime)
    {
        data class totalTime(@SerializedName("mobile") val total_mobile: String,
                             @SerializedName("laptop") val total_laptop : String)

        data class studyTime(@SerializedName("mobile") val total_mobile: String,
                             @SerializedName("laptop") val total_laptop : String)

        data class classTime(@SerializedName("mobile") val total_mobile: String,
                             @SerializedName("laptop") val total_laptop : String)

        data class freeTime(@SerializedName("mobile") val total_mobile: String,
                            @SerializedName("laptop") val total_laptop : String)
    }

}

