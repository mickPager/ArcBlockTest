package cn.mick.app.arcblocktest.api

import android.content.Context
import android.content.Intent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * create by Mick when 2023/1/28.
 */

const val BASE_URL = "https://arcblockio.cn/"

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value) {
        this.setCharAt(length-1, value)
    }


fun String.test() : String {
    return this
}

fun test() {
    println("OK")
}

class TestKotlin {

    companion object {

        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .build()


        fun startHomeActivity(intent: Intent?,context: Context?) {
            context?.startActivity(intent)
        }
    }

}

object ApiManager {

    val okHttpClient = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    init {
        TestKotlin.startHomeActivity(null,null)
    }
}