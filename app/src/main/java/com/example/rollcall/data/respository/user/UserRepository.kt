package com.example.rollcall.data.respository.user

import com.example.rollcall.data.model.Class
import retrofit2.http.Header
import retrofit2.http.Path
import javax.inject.Singleton


@Singleton
interface UserRepository {
    suspend fun getClassOfTeacher(
        tokenAdmin: String,
        id: String,
    ): Class


}