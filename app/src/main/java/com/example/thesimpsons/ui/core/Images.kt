package com.example.thesimpsons.ui.core

import androidx.compose.ui.graphics.vector.Path

object Images {

    const val PORTRAIT_SIZE = 500
    const val THUMBNAIL = 200
    const val LOCATION_IMAGE = 1280
    private const val PATH = "https://cdn.thesimpsonsapi.com/"

    fun createPath(size: Int, endpoint: String): String {
        return "${PATH}$size$endpoint"
    }

}