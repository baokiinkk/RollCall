package com.example.rollcall.data.api

import com.example.rollcall.data.model.Fruit
import retrofit2.http.GET
import javax.inject.Singleton

interface ApiService {
    @GET("v1/public/comics?ts=1&apikey=cf0551d555919dd8e11108ea1c17adf8&hash=c0220da4f22563f9d9b6c99fb704fa8a")
    suspend fun getBooks(): Fruit
}