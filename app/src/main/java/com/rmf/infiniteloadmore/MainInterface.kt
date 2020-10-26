package com.rmf.infiniteloadmore

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MainInterface {
    @GET("v2/list")
    fun stringCall(
        @Query("page") page : Int, @Query("limit") limit : Int
        ) : Call<String>
}