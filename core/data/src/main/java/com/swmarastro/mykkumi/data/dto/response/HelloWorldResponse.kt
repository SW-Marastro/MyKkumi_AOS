package com.swmarastro.mykkumi.data.dto.response

import com.google.gson.annotations.SerializedName

data class HelloWorldResponse(
    @SerializedName("title")
    val title: String
)