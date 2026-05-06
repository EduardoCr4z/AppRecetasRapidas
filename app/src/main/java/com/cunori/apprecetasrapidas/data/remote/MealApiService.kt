package com.cunori.apprecetasrapidas.data.remote

import retrofit2.http.GET

interface MealApiService {
    @GET("random.php")
    suspend fun getRandomRecipe(): MealResponseDto
}
