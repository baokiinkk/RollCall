package com.example.rollcall.data.respository.admin

import com.example.rollcall.data.model.*

import javax.inject.Singleton


@Singleton
interface AdminRepository{

   //------------------------ Users --------------------------------------
   suspend fun login(login:LoginUser): Users
   suspend fun getStudent(tokenAdmin:String):Users
   suspend fun getTeacher(tokenAdmin:String):Users
   suspend fun getDashBoard(tokenAdmin:String):DashBoard
   suspend fun createUser(tokenAdmin:String,user: UserPost):Boolean
   suspend fun editUser(tokenAdmin:String,user: UserPost):Users
   suspend fun editPasswordUser(tokenAdmin:String,user: UserPost):Users
   suspend fun deleteUser(tokenAdmin:String,user: UserPost):Users
   suspend fun logOut(token: String):Users

   //------------------------ Major --------------------------------------
   suspend fun getMajors(tokenAdmin:String):MajorReponse

   //------------------------ Class --------------------------------------
   suspend fun getClass(tokenAdmin:String):Class
   suspend fun createClass(tokenAdmin:String,classes: DataClass): Class
   suspend fun editClass(tokenAdmin:String,user: DataClass):Class
   suspend fun deleteClass(tokenAdmin:String,user: DataClass):Class
}