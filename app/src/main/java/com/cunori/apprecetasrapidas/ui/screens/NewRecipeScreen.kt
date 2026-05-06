package com.cunori.apprecetasrapidas.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cunori.apprecetasrapidas.data.repository.Recipe
import com.cunori.apprecetasrapidas.ui.components.HeaderCard

@Composable
fun NewRecipeScreen(onSave: (Recipe) -> Unit) {
    var title by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var instructions by rememberSaveable { mutableStateOf("") }
    val canSave = title.isNotBlank() && instructions.isNotBlank()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            HeaderCard(
                title = "Nueva receta",
                subtitle = "Anota una preparacion rapida y guardala en tu coleccion local."
            )
        }
        item {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nombre") },
                singleLine = true
            )
        }
        item {
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Categoria") },
                singleLine = true
            )
        }
        item {
            OutlinedTextField(
                value = instructions,
                onValueChange = { instructions = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                label = { Text("Instrucciones") }
            )
        }
        item {
            Button(
                onClick = {
                    onSave(
                        Recipe(
                            title = title,
                            category = category.ifBlank { "Rapida" },
                            area = "Casa",
                            instructions = instructions,
                            imageUrl = "",
                            source = "Receta local"
                        )
                    )
                    title = ""
                    category = ""
                    instructions = ""
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = canSave
            ) {
                Text("Guardar receta")
            }
        }
    }
}
