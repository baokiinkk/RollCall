package com.example.rollcall.data.model

data class DashBoard(val message: String? = null, val data: DashBoardDetail? = null)
data class DashBoardDetail(val student_count: Int, val teacher_count: Int, val class_count: Int)