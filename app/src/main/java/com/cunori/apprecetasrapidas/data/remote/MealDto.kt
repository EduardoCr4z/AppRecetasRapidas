package com.cunori.apprecetasrapidas.data.remote

import com.google.gson.annotations.SerializedName

data class MealResponseDto(
    @SerializedName("meals")
    val meals: List<MealDto>?
)

data class MealDto(
    @SerializedName("idMeal")
    val idMeal: String?,
    @SerializedName("strMeal")
    val name: String?,
    @SerializedName("strCategory")
    val category: String?,
    @SerializedName("strArea")
    val area: String?,
    @SerializedName("strInstructions")
    val instructions: String?,
    @SerializedName("strMealThumb")
    val imageUrl: String?,
    @SerializedName("strSource")
    val source: String?
)
