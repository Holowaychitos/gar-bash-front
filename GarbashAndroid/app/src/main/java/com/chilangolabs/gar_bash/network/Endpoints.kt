package com.chilangolabs.gar_bash.network

import com.chilangolabs.gar_bash.network.request.UserUpdatePickGarbage
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by gorrotowi on 03/02/18.
 */
public interface Endpoints {

    @POST("filter")
    fun postPickGarbash(@Body rq: UserUpdatePickGarbage): Call<UserUpdatePickGarbage>

}