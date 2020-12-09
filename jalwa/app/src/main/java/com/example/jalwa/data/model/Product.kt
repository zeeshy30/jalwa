package com.example.jalwa.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product (
        @SerializedName("_id")
        val id: String,

        val handle: String,
        val title: String,
        val body: String? = null,
        val vendor: String,
        val type: String,
        val tags: List<String>,
        val price: String,

        @SerializedName("photoUrl")
        val photoURL: String,
        @SerializedName("videoUrl")
        val videoURL: String
) : Serializable

