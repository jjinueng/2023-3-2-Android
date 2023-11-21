package com.example.a5

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnrollment(enrollment: Enrollment)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("SELECT * from student_table")
    fun getAllStudents(): LiveData<List<Student>>

    @Query("SELECT * from student_table where id = :sid")
    fun getStudentById(sid : Int) : Student

    @Query("SELECT * FROM enrollment WHERE studentId = :id")
    suspend fun getEnrollmentByStudentId(id: Int): Enrollment
}