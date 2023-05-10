package com.mkt.myapplication.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CocktailsCategoryDto(
    val categories: List<Category>
)