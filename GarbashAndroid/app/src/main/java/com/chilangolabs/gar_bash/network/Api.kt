package com.chilangolabs.gar_bash.network

import com.chilangolabs.gar_bash.network.request.UserUpdatePickGarbage
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by gorrotowi on 03/02/18.
 */
//'https://devweek.pagekite.me/filter'


object Api {

    var retrofit: Retrofit? = null
    var endpoints: Endpoints? = null


    fun initRetrofit() {
        endpoints = getClient()?.create(Endpoints::class.java)
    }

    fun updateGarbagePick(rq: UserUpdatePickGarbage, l: OnRqListener) {
        val call = Api.endpoints?.postPickGarbash(rq)

        call?.enqueue(object : Callback<UserUpdatePickGarbage> {
            override fun onResponse(call: Call<UserUpdatePickGarbage>?, response: Response<UserUpdatePickGarbage>?) {
                if (response?.code() == 200) {
                    l.success(response.body())
                } else {
                    l.error(Throwable("Response error ${response?.code()}"))
                }
            }

            override fun onFailure(call: Call<UserUpdatePickGarbage>?, t: Throwable?) {
                l.error(t ?: Throwable("Error"))
            }
        })
    }

}

private fun makeInterceptor(): Interceptor {
    return Interceptor { chain ->
        val request = chain.request().newBuilder()
        request.addHeader("Content-Type", "application/json")
        request.addHeader("Accept", "application/json")
        chain.proceed(request.build())
    }
}

private fun makeLogginInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return loggingInterceptor
}

private fun makeOkHttpClient(vararg interceptor: Interceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
    for (anInterceptor in interceptor) {
        builder.interceptors().add(anInterceptor)
    }
    return builder.build()
}

private fun getClient(): Retrofit? {
    val interceptor = makeInterceptor()
    val loggingInterceptor = makeLogginInterceptor()
    val client = makeOkHttpClient(interceptor, loggingInterceptor)
    if (Api.retrofit == null) {
        Api.retrofit = Retrofit.Builder()
                .baseUrl("https://devweek.pagekite.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }
    return Api.retrofit
}


interface OnRqListener {
    fun success(response: UserUpdatePickGarbage?)
    fun error(t: Throwable)
}