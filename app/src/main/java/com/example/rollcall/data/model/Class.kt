package com.example.rollcall.data.model

data class Class(
    val count: String? = null,
    val message: String? = null,
    val data: MutableList<DataClass>? = null
)
data class DataClass(
    val id:String? = null,
    val name:String? = null,
    val teacher:User? = null,
    val room:String? = null,
    val students:MutableList<User>? = null,
    val monitors:MutableList<User>? = null,
    val credit:String? = null,
    val dayOfWeek:String? = null,
    val shift:String? = null,
    val days:String? = null,
    val dayStart:String? = null
)
