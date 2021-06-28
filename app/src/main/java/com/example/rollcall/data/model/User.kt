package com.example.rollcall.data.model

data class LoginUser(val id: String, val password: String)
data class UserLogin(val user:User? = null,val message:String? = null)
data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val classes: MutableList<Class>,
    val token: String

)


