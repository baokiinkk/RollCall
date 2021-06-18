package com.example.rollcall.data.model

data class Detail(val results:MutableList<Results>)
data class Results(val title:String)
data class Fruit(val data:Detail)


