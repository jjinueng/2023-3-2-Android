package com.example.a5

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Student(
    @PrimaryKey val id: Int,
    val name: String
)

@Entity(tableName = "enrollment")
data class Enrollment(
    @PrimaryKey val studentId: Int,
    val classId: Int
)