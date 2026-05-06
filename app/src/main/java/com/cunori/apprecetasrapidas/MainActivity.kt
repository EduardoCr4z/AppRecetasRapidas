package com.cunori.apprecetasrapidas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cunori.apprecetasrapidas.data.local.AppDatabase
import com.cunori.apprecetasrapidas.data.remote.RetrofitProvider
import com.cunori.apprecetasrapidas.data.repository.RecipeRepository
import com.cunori.apprecetasrapidas.ui.navigation.QuickRecipesApp
import com.cunori.apprecetasrapidas.ui.theme.AppRecetasRapidasTheme
import com.cunori.apprecetasrapidas.viewmodel.RecipeViewModel
import com.cunori.apprecetasrapidas.viewmodel.RecipeViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getInstance(applicationContext)
        val repository = RecipeRepository(
            dao = database.savedRecipeDao(),
            api = RetrofitProvider.mealApi
        )

        setContent {
            AppRecetasRapidasTheme {
                val viewModel: RecipeViewModel = viewModel(
                    factory = RecipeViewModelFactory(repository)
                )
                QuickRecipesApp(viewModel = viewModel)
            }
        }
    }
}
