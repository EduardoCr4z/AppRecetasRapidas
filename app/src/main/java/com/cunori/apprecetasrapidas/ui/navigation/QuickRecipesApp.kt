package com.cunori.apprecetasrapidas.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.cunori.apprecetasrapidas.ui.screens.DiscoverScreen
import com.cunori.apprecetasrapidas.ui.screens.NewRecipeScreen
import com.cunori.apprecetasrapidas.ui.screens.RecipeListScreen
import com.cunori.apprecetasrapidas.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickRecipesApp(viewModel: RecipeViewModel) {
    var selectedScreen by rememberSaveable { mutableStateOf(RecipeScreen.Discover) }
    val uiState by viewModel.uiState.collectAsState()
    val savedRecipes by viewModel.savedRecipes.collectAsState()
    val favoriteRecipes by viewModel.favoriteRecipes.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Recetas Rapidas",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                tonalElevation = NavigationBarDefaults.Elevation
            ) {
                RecipeScreen.entries.forEach { screen ->
                    NavigationBarItem(
                        selected = selectedScreen == screen,
                        onClick = { selectedScreen = screen },
                        icon = {
                            Text(
                                text = screen.indicator,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        label = { Text(screen.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            when (selectedScreen) {
                RecipeScreen.Discover -> DiscoverScreen(
                    recipe = uiState.randomRecipe,
                    isLoading = uiState.isLoading,
                    onLoadRandom = viewModel::loadRandomRecipe,
                    onSave = viewModel::saveCurrentRandomRecipe
                )

                RecipeScreen.Saved -> RecipeListScreen(
                    title = "Recetas guardadas",
                    emptyMessage = "Aun no hay recetas guardadas.",
                    recipes = savedRecipes,
                    onToggleFavorite = viewModel::toggleFavorite,
                    onDelete = viewModel::deleteRecipe
                )

                RecipeScreen.Favorites -> RecipeListScreen(
                    title = "Lista de favoritas",
                    emptyMessage = "Marca recetas como favoritas para verlas aqui.",
                    recipes = favoriteRecipes,
                    onToggleFavorite = viewModel::toggleFavorite,
                    onDelete = viewModel::deleteRecipe
                )

                RecipeScreen.NewRecipe -> NewRecipeScreen(
                    onSave = viewModel::saveRecipe
                )
            }
        }
    }
}
