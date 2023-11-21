package com.example.a7

import android.content.Context
import android.widget.EditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MyRepository(context: Context) {
    private val baseURL = "https://api.github.com/"
    private val api = retrofitInit()

    private fun retrofitInit(): RestApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit.create(RestApi::class.java)
    }

    private val myDao = MyDatabase.getDatabase(context).myDao
    val repos = myDao.getAll()
    suspend fun refreshData(userName: String) {
        withContext(Dispatchers.IO) {
            val repos = api.listRepos(userName)
            val repoDs = repos.map {
                RepoD(it.name, it.owner.login)
            }
            myDao.insertAll(repoDs)
        }
    }
}