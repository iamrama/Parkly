package com.rk.parkly.util

import java.text.SimpleDateFormat
import java.util.Locale

class DateUtil {
    companion object{
        fun changeDateFormat(strDate: String?): String {
            if(strDate.isNullOrEmpty()){
                return ""
            }
            return try{
                val sourceSdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val requiredSdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                requiredSdf.format(sourceSdf.parse(strDate))
            }catch (ex: Exception){
                ""
            }
        }
    }
}