package com.example.rollcall.data.model

import java.io.Serializable

data class Report(
    val id: String,
    val subject: String,
    val qrUrl:String,
    val content: MutableList<Content>,
): Serializable
data class DataReport(val count:Int, val data:MutableList<Report>,val message: String? = null)
data class ReportBody(val checkinLimitTime: String, val allowLate: String)

data class Content(val user: User, val status: String)