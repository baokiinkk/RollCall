package com.example.rollcall.data.respository

import com.example.rollcall.data.model.Fruit
import javax.inject.Singleton


@Singleton
interface Repository{
   suspend fun getData(get: (Fruit) -> Unit)
}