package com.chilangolabs.gar_bash.network.request

import com.google.gson.annotations.SerializedName

data class UserUpdatePickGarbage(

        @field:SerializedName("owner")
        val owner: String? = null,

        @field:SerializedName("days")
        val days: List<Int?>? = null,

        @field:SerializedName("location")
        val location: Location? = null,

        @field:SerializedName("time")
        val time: Int? = null
)

data class Location(

        @field:SerializedName("lon")
        val lon: Double? = null,

        @field:SerializedName("lat")
        val lat: Double? = null
)