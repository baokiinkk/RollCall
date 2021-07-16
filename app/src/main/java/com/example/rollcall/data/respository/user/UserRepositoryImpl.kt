package com.example.rollcall.data.respository.user

import com.example.rollcall.data.api.ApiService
import com.example.rollcall.data.model.Class
import com.example.rollcall.data.model.Message
import com.example.rollcall.data.model.User
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.user.UserRepository
import com.google.gson.Gson
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService) : UserRepository{
    override suspend fun getClassUser(token: String, id: String, user: String): Class =
            try {
                apiService.getClassUser(token, id, user)
            } catch (cause: HttpException) {
                Gson().fromJson(cause.response()?.errorBody()?.string(),Class::class.java)

            }

    override suspend fun getInfoUser(token: String, id: String): Users =
        try {
            apiService.getInfoUser(token,id)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(),Users::class.java)

        }

    override suspend fun checkin(token: String, id: String) : Message =
        try {
            apiService.checkin(token, id)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(),Message::class.java)
        }

    override suspend fun checkinByTeacher(token: String, id: String, user: User): Message =
        try {
            apiService.checkinByTeacher(token, id, user)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(),Message::class.java)
        }
}