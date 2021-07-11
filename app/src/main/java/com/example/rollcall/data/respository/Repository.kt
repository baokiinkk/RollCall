package com.example.rollcall.data.respository

import com.example.rollcall.data.model.DashBoard
import com.example.rollcall.data.model.LoginUser
import com.example.rollcall.data.model.User

import com.example.rollcall.data.model.Users
import javax.inject.Singleton


@Singleton
interface Repository{
   suspend fun login(login:LoginUser): Users
   suspend fun getStudent(tokenAdmin:String):Users
   suspend fun getTeacher(tokenAdmin:String):Users
   suspend fun getDashBoard(tokenAdmin:String):DashBoard
   suspend fun createUser(tokenAdmin:String,user: User):Users
}