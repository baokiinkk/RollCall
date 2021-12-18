package com.example.rollcall.data.model


data class MajorReponse(
    val count: String? = null,
    val message: String? = null,
    val data: MutableList<Major>? = null
)
data class Major(
    val departmentId: DepartmentId?=null,
    val majorId: String,
    val name: String?=null
)
data class DepartmentId(
    val departmentId: String,
    val name: String
)
