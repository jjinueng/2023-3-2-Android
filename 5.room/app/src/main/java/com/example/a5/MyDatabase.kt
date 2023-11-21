package com.example.a5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class, Enrollment::class], exportSchema = false, version = 2)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getMyDao(): MyDAO

    companion object {
        fun getDatabase(context: Context) : MyDatabase {
            val builder = Room.databaseBuilder(context, MyDatabase::class.java, "school_database")
            return builder.build()
        }

        fun getEnrollmentDatabase(context: Context) : MyDatabase {
            val builder = Room.databaseBuilder(context, MyDatabase::class.java, "enrollment")
            return builder.build()
        }
    }
}