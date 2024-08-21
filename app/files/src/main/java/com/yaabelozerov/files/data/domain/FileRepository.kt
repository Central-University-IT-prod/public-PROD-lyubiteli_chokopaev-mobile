package com.yaabelozerov.files.data.domain

import android.net.Uri
import java.net.URI

interface FileRepository {
    fun uploadImage()
    suspend fun uploadXlsx(uri: Uri)
}