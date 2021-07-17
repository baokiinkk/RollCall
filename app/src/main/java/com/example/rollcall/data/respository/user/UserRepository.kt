package com.example.rollcall.data.respository.user

import com.example.rollcall.data.model.Class
import com.example.rollcall.data.model.Users

import javax.inject.Singleton


@Singleton
interface UserRepository {
    suspend fun getClassOfTeacher(
        tokenAdmin: String,
        id: String,
    ): Class

    suspend fun getInfoUser(
        token: String,
        id: String,
    ): Users

}