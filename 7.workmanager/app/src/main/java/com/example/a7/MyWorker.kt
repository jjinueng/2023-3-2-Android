package com.example.a7

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class MyWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        println("호출!")
        val username = inputData.getString("username") ?: ""
        val repository = MyRepository(applicationContext)
        try {
            repository.refreshData(username)
        } catch (e: Exception) {
            return Result.retry()
        }
        return Result.success()
    }
    companion object {
        const val name = "com.example.repository_pattern.MyWorker"
    }
}