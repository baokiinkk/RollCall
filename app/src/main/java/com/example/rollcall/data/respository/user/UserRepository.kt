package com.example.rollcall.data.respository.user

import com.example.rollcall.data.model.Class
import com.example.rollcall.data.model.Message
import com.example.rollcall.data.model.User
import com.example.rollcall.data.model.Users

import javax.inject.Singleton


@Singleton
interface UserRepository {
    suspend fun getClassUser(
        tokenAdmin: String,
        id: String,
        user: String,
    ): Class

    suspend fun getInfoUser(
        token: String,
        id: String,
    ): Users

    suspend fun checkin(
        token: String,
        id: String
    ): Message

    suspend fun checkinByTeacher(
        token: String,
        id: String,
        user: User
    ): Message
}