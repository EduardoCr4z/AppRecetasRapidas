# Recetas Rapidas

Aplicacion Android desarrollada con Kotlin y Jetpack Compose para consultar recetas aleatorias desde Internet y guardar recetas locales en el dispositivo.

## Integrantes

- Eduardo Rubén Cruz Sánchez 202146471
- Denis Roberto Lau Ardón 201544138

## Funcionalidades principales

- Consulta una receta aleatoria desde TheMealDB usando Retrofit.
- Guarda recetas en una base de datos local con Room.
- Muestra recetas guardadas en una lista desplazable con tarjetas.
- Permite marcar recetas como favoritas y verlas en una pantalla separada.
- Permite eliminar recetas guardadas.
- Permite crear recetas manualmente desde un formulario Compose.

## API utilizada

- TheMealDB: `https://www.themealdb.com/api/json/v1/1/random.php`

## Conceptos aplicados

- **Room:** se usa `SavedRecipeEntity`, `SavedRecipeDao` y `AppDatabase` para insertar, consultar, actualizar favoritos y eliminar recetas.
- **Retrofit:** `MealApiService` consume el endpoint `random.php` con corrutinas.
- **Repository:** `RecipeRepository` abstrae el origen de datos local y remoto para que la UI no dependa directamente de Room o Retrofit.
- **ViewModel:** `RecipeViewModel` expone estados con `StateFlow`, maneja carga, exito, error y acciones de guardar, favorito y eliminar.
- **Compose:** la interfaz usa `Scaffold`, `TopAppBar`, `NavigationBar`, `LazyColumn`, `Card`, `Button`, `OutlinedTextField`, animacion simple y descripciones accesibles.

## Prueba local

Incluye una prueba en `RecipeMappingTest` para validar la transformacion de datos de la API hacia el modelo de dominio.
