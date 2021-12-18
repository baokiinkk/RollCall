package com.example.rollcall.data.model

import java.io.Serializable

data class Class(
    val count: String? = null,
    val message: String? = null,
    val data: MutableList<SubjectResponse>? = null
)
data class DataClass(
    val id:String,
    val name:String,
    val teacher:User? = null,
    val room:String? = null,
    var students:MutableList<User>? = null,
    val monitors:MutableList<User>? = null,
    val credit:Int? = null,
    val dayOfWeek:String? = null,
    val shift:String? = null,
    val days:Int? = null,
    val dateStart:String? = null
):Serializable

data class CreateSubjectPost(
    val credits: Int,
    val dayOfWeek: String,
    val days: Int,
    val name: String,
    val percentDiligence: Int,
    val percentExam: Int,
    val percentPractice: Int,
    val percentSerminar: Int,
    val percentTest: Int,
    val roomId: String,
    val semester: String,
    val shift: Int,
    val startDate: String,
    val subjectId: String
)

data class SubjectResponse(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val credits: Int,
    val dayOfWeek: String,
    val days: Int,
    val isFinished: Boolean,
    val name: String,
    val percentDiligence: Int,
    val percentExam: Int,
    val percentPractice: Int,
    val percentSerminar: Int,
    val percentTest: Int,
    val roomId: RoomId,
    val schedule: MutableList<Any>,
    val semester: String,
    val shift: Int,
    val startDate: String,
    val status: String,
    val students: MutableList<User>,
    val subjectId: String,
    val teacher: User,
    val updatedAt: String
):Serializable
