package com.example.rollcall.data.respository

import android.util.Log
import com.example.rollcall.data.api.ApiService
import com.example.rollcall.data.model.Fruit
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val apiService: ApiService) : Repository {
    override suspend fun getData(get: (Fruit) -> Unit) {
        try {
            Log.d("quocbao",apiService.getBooks().toString())
            get(apiService.getBooks())
        }
        catch (e:Exception){
            Log.d("quocbao","error")

        }
    }
}