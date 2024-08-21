package com.yaabelozerov.files.data.remote

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface XlsxApiService {
    @Multipart
    @POST("{id}/upload") // TODO:
    fun uploadExcelFile(
        @Path("id") eventId: Int,
        @Part("OrganizerId") organizerId: Int,
        @Part("MembersPath") membersPath: String,
        @Part file: MultipartBody.Part?
    ): Call<Unit>
}
//
//@JsonClass(generateAdapter = true)
//data class UploadRequestResponse(
//    val status: String
//)