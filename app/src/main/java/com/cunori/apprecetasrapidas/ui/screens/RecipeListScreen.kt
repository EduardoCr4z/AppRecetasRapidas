package com.cunori.apprecetasrapidas.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cunori.apprecetasrapidas.data.repository.Recipe
import com.cunori.apprecetasrapidas.ui.components.EmptyState
import com.cunori.apprecetasrapidas.ui.components.RecipeCard

@Composable
fun RecipeListScreen(
    title: String,
    emptyMessage: String,
    recipes: List<Recipe>,
    onToggleFavorite: (Recipe) -> Unit,
    onDelete: (Recipe) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${recipes.size} recetas disponibles",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (recipes.isEmpty()) {
            item {
                EmptyState(message = emptyMessage)
            }
        }

        items(recipes, key = { it.id }) { recipe ->
            RecipeCard(
                recipe = recipe,
                isRemote = false,
                onToggleFavorite = { onToggleFavorite(recipe) },
                onDelete = { onDelete(recipe) }
            )
        }
    }
}
