package com.example.rollcall.data.model

data class LoginUser(val id: String, val password: String)
data class Users(val count:String? = null,val message:String? = null,val data:MutableList<User>? = null)
data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val password:String,
    val classes: MutableList<Class>? = null,
    val token: String? = null

)


