package com.cunori.apprecetasrapidas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cunori.apprecetasrapidas.data.repository.Recipe
import com.cunori.apprecetasrapidas.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class RecipeUiState(
    val randomRecipe: Recipe? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

class RecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    val savedRecipes: StateFlow<List<Recipe>> = repository.savedRecipes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val favoriteRecipes: StateFlow<List<Recipe>> = repository.favoriteRecipes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    init {
        loadRandomRecipe()
    }

    fun loadRandomRecipe() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, message = null)
            repository.fetchRandomRecipe()
                .onSuccess { recipe ->
                    _uiState.value = RecipeUiState(randomRecipe = recipe)
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = throwable.message ?: "No se pudo cargar la receta."
                    )
                }
        }
    }

    fun saveCurrentRandomRecipe() {
        val recipe = uiState.value.randomRecipe ?: return
        saveRecipe(recipe)
    }

    fun saveRecipe(recipe: Recipe) {
        viewModelScope.launch {
            runCatching { repository.saveRecipe(recipe) }
                .onSuccess {
                    _uiState.value = _uiState.value.copy(message = "Receta guardada correctamente.")
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        message = throwable.message ?: "No se pudo guardar la receta."
                    )
                }
        }
    }

    fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch {
            repository.toggleFavorite(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.deleteRecipe(recipe)
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
}

class RecipeViewModelFactory(
    private val repository: RecipeRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
