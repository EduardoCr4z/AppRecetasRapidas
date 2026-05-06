package com.cunori.apprecetasrapidas.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cunori.apprecetasrapidas.data.repository.Recipe
import com.cunori.apprecetasrapidas.ui.components.HeaderCard
import com.cunori.apprecetasrapidas.ui.components.RecipeCard

@Composable
fun DiscoverScreen(
    recipe: Recipe?,
    isLoading: Boolean,
    onLoadRandom: () -> Unit,
    onSave: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeaderCard(
                title = "Receta aleatoria",
                subtitle = "Inspira tu siguiente comida y guarda las recetas que quieras preparar despues."
            )
        }

        item {
            AnimatedVisibility(visible = isLoading) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Buscando una buena idea...",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        Text(
                            text = "Conectando con TheMealDB",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        recipe?.let {
            item {
                RecipeCard(
                    recipe = it,
                    isRemote = true,
                    onToggleFavorite = null,
                    onDelete = null
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onLoadRandom,
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading
                ) {
                    Text("Otra receta")
                }
                OutlinedButton(
                    onClick = onSave,
                    modifier = Modifier.weight(1f),
                    enabled = recipe != null
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}
