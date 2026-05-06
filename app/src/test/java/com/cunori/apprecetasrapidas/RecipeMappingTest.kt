package com.cunori.apprecetasrapidas

import com.cunori.apprecetasrapidas.data.remote.MealDto
import com.cunori.apprecetasrapidas.data.repository.toRecipe
import org.junit.Assert.assertEquals
import org.junit.Test

class RecipeMappingTest {
    @Test
    fun mealDtoToRecipe_usesReadableFallbacks() {
        val recipe = MealDto(
            idMeal = "1",
            name = "",
            category = null,
            area = "",
            instructions = null,
            imageUrl = null,
            source = ""
        ).toRecipe()

        assertEquals("Receta sin nombre", recipe.title)
        assertEquals("General", recipe.category)
        assertEquals("Internacional", recipe.area)
        assertEquals("Sin instrucciones disponibles.", recipe.instructions)
        assertEquals("TheMealDB", recipe.source)
    }
}
