package com.example.rollcall.data.respository

import com.example.rollcall.data.model.LoginUser
import com.example.rollcall.data.model.User
import com.example.rollcall.data.model.UserLogin
import javax.inject.Singleton


@Singleton
interface Repository{
   suspend fun login(login:LoginUser): UserLogin
}