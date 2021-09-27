package com.example.myweatherapp.base


import android.text.TextUtils
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection

class CoreNetworkError : Throwable {
    var error: Throwable? = null
    var errorCode = 0

    companion object {
        const val DEFAULT_ERROR_MESSAGE = "Bir sorun oluştu lütfen tekrar deneyiniz"
        const val NETWORK_ERROR_MESSAGE = "İnternet bağlantınız yok. Lütfen bağlantıyı kontrol edeniz"
        private const val ERROR_MESSAGE_HEADER = "Error-Message"
    }

    constructor(code: Int, e: Throwable?) : super(e) {
        error = e
        errorCode = code
    }

    constructor(e: Throwable) {
        try {
            CoreNetworkError((e as HttpException).code(), e)
            error = e
            errorCode = e.code()
        } catch (a: Exception) {
            CoreNetworkError(-1, e)
            error = e
            errorCode = -1
        }
    }

    override val message: String
        get() = error!!.message!!

    val isAuthFailure: Boolean
        get() = error is HttpException &&
                (error as HttpException).code() == HttpURLConnection.HTTP_UNAUTHORIZED

    val isResponseNull: Boolean
        get() = error is HttpException && (error as HttpException).response() == null

    val appErrorMessage: String?
        get() {
            if (error is IOException) return NETWORK_ERROR_MESSAGE
            if (error !is HttpException) return DEFAULT_ERROR_MESSAGE
            val response = (error as HttpException?)!!.response()
            if (response != null) {
                val status = getJsonStringFromResponse(response)
                if (!TextUtils.isEmpty(status)) return status
                val headers = response.headers().toMultimap()
                if (headers.containsKey(ERROR_MESSAGE_HEADER)) return headers[ERROR_MESSAGE_HEADER]!![0]
            }
            return DEFAULT_ERROR_MESSAGE
        }

    protected fun getJsonStringFromResponse(response: Response<*>): String? {
        return try {
            val jsonString = response.errorBody()!!.string()
            val errorResponse = Gson().fromJson(jsonString, okhttp3.Response::class.java)
            errorResponse.message
        } catch (e: Exception) {
            null
        }
    }



}