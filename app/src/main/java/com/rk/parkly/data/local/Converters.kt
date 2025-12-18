package com.rk.parkly.data.local

import androidx.room.TypeConverter
import com.rk.parkly.data.model.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source?): String?{
        return source?.name
    }

    @TypeConverter
    fun toSource(name:String?): Source? {
        return name?.let { Source(it,it) }
    }



    }
