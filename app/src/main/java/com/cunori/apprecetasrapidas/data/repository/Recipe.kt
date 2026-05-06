package com.cunori.apprecetasrapidas.data.repository

import com.cunori.apprecetasrapidas.data.local.SavedRecipeEntity
import com.cunori.apprecetasrapidas.data.remote.MealDto

data class Recipe(
    val id: Long = 0,
    val title: String,
    val category: String,
    val area: String,
    val instructions: String,
    val imageUrl: String,
    val source: String,
    val isFavorite: Boolean = false
)

fun MealDto.toRecipe(): Recipe {
    return Recipe(
        title = name.orEmpty().ifBlank { "Receta sin nombre" },
        category = category.orEmpty().ifBlank { "General" },
        area = area.orEmpty().ifBlank { "Internacional" },
        instructions = instructions.orEmpty().ifBlank { "Sin instrucciones disponibles." },
        imageUrl = imageUrl.orEmpty(),
        source = source.orEmpty().ifBlank { "TheMealDB" }
    )
}

fun SavedRecipeEntity.toRecipe(): Recipe {
    return Recipe(
        id = id,
        title = title,
        category = category,
        area = area,
        instructions = instructions,
        imageUrl = imageUrl,
        source = source,
        isFavorite = isFavorite
    )
}

fun Recipe.toEntity(): SavedRecipeEntity {
    return SavedRecipeEntity(
        id = id,
        title = title.trim(),
        category = category.trim().ifBlank { "General" },
        area = area.trim().ifBlank { "Casa" },
        instructions = instructions.trim(),
        imageUrl = imageUrl.trim(),
        source = source.trim().ifBlank { "Receta local" },
        isFavorite = isFavorite
    )
}
