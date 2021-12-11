package com.android.crud.network

import com.android.crud.model.ResponseCreateKaryawan
import com.android.crud.model.ResponseDeleteKaryawan
import com.android.crud.model.ResponseKaryawan
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RestApi {

    @GET("getKaryawan")
    fun getKaryawan(): Flowable<ResponseKaryawan>

    @GET("getDetailsKaryawan")
    fun getDetailsKaryawan(
        @Query("id") id: String
    ): Flowable<ResponseKaryawan>

    @FormUrlEncoded
    @POST("createKaryawan")
    fun addKaryawan(
        @Field("nama") nama:String,
        @Field("email") email:String,
        @Field("alamat") alamat:String,
    ):Single<ResponseCreateKaryawan>

    @GET("deleteKaryawan")
    fun deleteKaryawan(
        @Query("id") id: String
    ): Flowable<ResponseDeleteKaryawan>

}