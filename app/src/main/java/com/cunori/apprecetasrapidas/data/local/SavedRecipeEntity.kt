package com.cunori.apprecetasrapidas.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_recipes")
data class SavedRecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val category: String,
    val area: String,
    val instructions: String,
    val imageUrl: String,
    val source: String,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
