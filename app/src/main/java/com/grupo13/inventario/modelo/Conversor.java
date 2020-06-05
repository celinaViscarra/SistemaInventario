package com.grupo13.inventario.modelo;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.sql.Date;
import java.sql.Time;

public class Conversor {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Long timeToMillis(Time time){
        return time == null ? null : time.getTime();
    }

    @TypeConverter
    public static Time fromMillis(Long value){
        return value == null ? null : new Time(value);
    }
}
