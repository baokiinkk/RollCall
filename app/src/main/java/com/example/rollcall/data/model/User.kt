package com.example.rollcall.data.model

import java.io.Serializable

data class LoginUser(val id: String, val password: String)
data class Users(
    val count: String? = null,
    val message: String? = null,
    val data: MutableList<User>? = null
)

data class User(
    val status:String?=null,
    val userId: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val name: String,
    val email: String,
    val role: String? = null,
    val password: String? = null,
    val avtUrl: String? = null,
    val qrUrl: String? = null,
    val majorId: Major,
    val classes: MutableList<String>? = null,
    val token: String? = null,
    val birthplace: String? = null,
    val birthDate: String? = null,
    val phone: String? = null,
    var sex: String? = null,
    val idNo: String? = null,
    val address: String? = null,
    var selected: Boolean = false
) : Serializable


data class UserPost(
    val userId: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val name: String? = null,
    val email: String,
    val role: String? = null,
    val password: String? = null,
    val avtUrl: String? = null,
    val qrUrl: String? = null,
    val majorId: String?=null,
    val birthDate: String?=null,
    val birthplace: String? = null,
    val phone: String? = null,
    val sex: Int? = null,
) : Serializable


data class UserId(
    val studentId: String
)



