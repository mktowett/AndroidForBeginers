package com.mkt.myapplication.network

import com.mkt.myapplication.dto.CocktailsCategoryDto
import com.mkt.myapplication.dto.DrinksResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("list.php")
    fun getCategoryList(@Query("c") category: String?): Call<String?>?

    @GET("filter.php")
    fun getDrinksPerCategoryList(@Query("c") category: String?): Call<String?>?

}