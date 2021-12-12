package com.example.rollcall.data.model

import java.io.Serializable

data class LoginUser(val id: String, val password: String)
data class Users(
    val count: String? = null,
    val message: String? = null,
    val data: MutableList<User>? = null
)

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: String? = null,
    val old_password:String? = null,
    val password: String? = null,
    val avtUrl: String? = null,
    val qrUrl:String? = null,
    val classes: MutableList<String>? = null,
    val token: String? = null,
    var selected:Boolean = false
):Serializable

data class UserId(
    val studentId: String
)



