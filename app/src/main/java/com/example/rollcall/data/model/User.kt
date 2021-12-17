package com.example.rollcall.data.model

import java.io.Serializable
import java.util.*

data class LoginUser(val id: String, val password: String)
data class Users(
    val count: String? = null,
    val message: String? = null,
    val data: MutableList<User>? = null
)

data class User(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: String? = null,
    val password: String? = null,
    val avtUrl: String? = null,
    val qrUrl:String? = null,
    val majorId:String? = null,
    val classes: MutableList<String>? = null,
    val token: String? = null,
    val birthplace: String? = null,
    val birthDate: String? = null,
    val phone: String? = null,
    val sex: String? = null,
    val idNo: String? = null,
    val address: String? = null,
    var selected:Boolean = false
):Serializable

data class UserId(
    val studentId: String
)



