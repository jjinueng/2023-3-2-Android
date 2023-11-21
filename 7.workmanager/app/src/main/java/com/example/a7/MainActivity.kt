package com.example.a7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(emptyList())

        myViewModel = ViewModelProvider(this, MyViewModel.Factory(this))[MyViewModel::class.java]
        myViewModel.repos.observe(this) { reposD ->
            val repos = reposD.map { Repo(it.name, Owner(it.owner), "") }
            recyclerView.adapter = MyAdapter(repos)
        }

        findViewById<Button>(R.id.startWorker).setOnClickListener {
            startWorker()
        }

        findViewById<Button>(R.id.stopWorker).setOnClickListener {
            stopWorker()
        }
    }

    private fun startWorker() {
        val username = findViewById<EditText>(R.id.editUsername).text.toString()

        val constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.UNMETERED)
            setRequiresBatteryNotLow(true)
        }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(workDataOf("username" to username))
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            MyWorker.name,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    private fun stopWorker() {
        WorkManager.getInstance(this).cancelUniqueWork(MyWorker.name)
    }
}