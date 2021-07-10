package com.example.rollcall.data.respository

import com.example.rollcall.data.model.LoginUser

import com.example.rollcall.data.model.Users
import javax.inject.Singleton


@Singleton
interface Repository{
   suspend fun login(login:LoginUser): Users
   suspend fun getUses(tokenAdmin:String):Users
}