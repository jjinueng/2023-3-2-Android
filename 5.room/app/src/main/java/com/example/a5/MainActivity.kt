package com.example.a5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = MyDatabase.getDatabase(this)
        val dao = db.getMyDao()

        CoroutineScope(Dispatchers.IO).launch {
            dao.insertStudent(Student(1, "james"))
            dao.insertStudent(Student(2, "john"))
        }

        val allStudents = dao.getAllStudents()
        allStudents.observe(this) {
            val str = StringBuilder().apply {
                for ((id, name) in it) {
                    append(id)
                    append("-")
                    append(name)
                    append("\n")
                }
            }.toString()
            val textView = findViewById<TextView>(R.id.text_student_list)
            textView.text = str
        }

        val enrollmentDao = db.getMyDao()

        findViewById<Button>(R.id.query_student).setOnClickListener {
            val id = findViewById<EditText>(R.id.edit_student_id).text.toString().toInt()
            CoroutineScope(Dispatchers.IO).launch {
                val str = StringBuilder().apply {
                    val student = dao.getStudentById(id)
                    append(student.id)
                    append("-")
                    append(student.name)
                    append(":")
                    try {
                        val enrollment = enrollmentDao.getEnrollmentByStudentId(id)
                        append(enrollment.classId)
                        append("(c-lang),")
                    } catch (_: Exception) { }
                }.toString()
                val textView = findViewById<TextView>(R.id.text_query_student)
                textView.text = str
            }
        }

        findViewById<Button>(R.id.add_student).setOnClickListener {
            val id = findViewById<EditText>(R.id.edit_student_id).text.toString()
            val name = findViewById<EditText>(R.id.edit_student_name).text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                dao.insertStudent(Student(id.toInt(), name))
            }
        }

        findViewById<Button>(R.id.del_student).setOnClickListener {
            val id = findViewById<EditText>(R.id.edit_student_id).text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                dao.deleteStudent(Student(id.toInt(), " "))
            }
        }

        findViewById<Button>(R.id.enroll).setOnClickListener {
            val id = findViewById<EditText>(R.id.edit_student_id).text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                dao.insertEnrollment(Enrollment(id.toInt(), 1))
            }
        }
    }
}