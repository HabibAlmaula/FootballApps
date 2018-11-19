package com.example.mediacenterfkam.footballappssubs_2.Utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat

object ConvertDate{

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(date: String, format: String): String {
        var result = ""

        try {
            val stringToDate = SimpleDateFormat("yyyy-MM-dd").parse(date)
            val formater = SimpleDateFormat(format)

            result = formater.format(stringToDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return result
    }

    fun newDate(date: String): String {
        return formatDate(date, "EEEE, dd MMMM yyyy")
    }

}