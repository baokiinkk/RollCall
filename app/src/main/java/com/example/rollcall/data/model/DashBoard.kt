package com.example.rollcall.data.model

data class DashBoard(val message: String? = null, val data: MutableList<DashBoardDetail>? = null)
data class DashBoardDetail(val student_count: String, val teacher_count: String, val class_count: String)