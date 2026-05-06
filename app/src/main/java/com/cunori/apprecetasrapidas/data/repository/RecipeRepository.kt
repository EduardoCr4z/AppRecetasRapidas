package com.cunori.apprecetasrapidas.data.repository

import com.cunori.apprecetasrapidas.data.local.SavedRecipeDao
import com.cunori.apprecetasrapidas.data.remote.MealApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeRepository(
    private val dao: SavedRecipeDao,
    private val api: MealApiService
) {
    val savedRecipes: Flow<List<Recipe>> = dao.observeRecipes().map { recipes ->
        recipes.map { it.toRecipe() }
    }

    val favoriteRecipes: Flow<List<Recipe>> = dao.observeFavoriteRecipes().map { recipes ->
        recipes.map { it.toRecipe() }
    }

    suspend fun fetchRandomRecipe(): Result<Recipe> {
        return runCatching {
            val meal = api.getRandomRecipe().meals?.firstOrNull()
                ?: error("No se recibieron recetas desde la API.")
            meal.toRecipe()
        }
    }

    suspend fun saveRecipe(recipe: Recipe) {
        require(recipe.title.isNotBlank()) { "El nombre de la receta es obligatorio." }
        require(recipe.instructions.isNotBlank()) { "Las instrucciones son obligatorias." }
        dao.insert(recipe.toEntity())
    }

    suspend fun toggleFavorite(recipe: Recipe) {
        dao.update(recipe.copy(isFavorite = !recipe.isFavorite).toEntity())
    }

    suspend fun deleteRecipe(recipe: Recipe) {
        dao.delete(recipe.toEntity())
    }
}
