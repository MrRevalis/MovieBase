package com.example.moviebase.API

import android.util.Log
import retrofit2.Response
import java.io.IOException

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            Log.d("komunikat", response.body()!!.toString())
            return response.body()!!
        } else {
            Log.d("komunikat", "BLAD")
            throw ApiException(response.code().toString())
        }
    }
}

class ApiException(message: String) : IOException(message)