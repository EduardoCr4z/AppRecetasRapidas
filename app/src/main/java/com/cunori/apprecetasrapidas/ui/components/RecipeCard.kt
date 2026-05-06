package com.cunori.apprecetasrapidas.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cunori.apprecetasrapidas.data.repository.Recipe
import com.cunori.apprecetasrapidas.ui.theme.AppRecetasRapidasTheme

@Composable
fun RecipeCard(
    recipe: Recipe,
    isRemote: Boolean,
    onToggleFavorite: (() -> Unit)?,
    onDelete: (() -> Unit)?
) {
    val scale by animateFloatAsState(
        targetValue = if (recipe.isFavorite) 1.03f else 1f,
        label = "favorite-card-scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RecipeImagePlaceholder(recipeTitle = recipe.title)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${recipe.category} - ${recipe.area}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = recipe.category.ifBlank { "Rapida" },
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = recipe.instructions,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if (isRemote) 8 else 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Fuente: ${recipe.source}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            if (!isRemote) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = { onToggleFavorite?.invoke() }) {
                        Text(if (recipe.isFavorite) "Quitar favorita" else "Favorita")
                    }
                    TextButton(onClick = { onDelete?.invoke() }) {
                        Text("Eliminar", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeCardPreview() {
    AppRecetasRapidasTheme {
        RecipeCard(
            recipe = Recipe(
                title = "Tacos rapidos de pollo",
                category = "Cena",
                area = "Guatemala",
                instructions = "Calienta tortillas, agrega pollo sazonado, vegetales y salsa.",
                imageUrl = "",
                source = "Vista previa",
                isFavorite = true
            ),
            isRemote = false,
            onToggleFavorite = {},
            onDelete = {}
        )
    }
}
