package com.moodi.task.screen.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.moodi.domain.model.GiphyAppModel
import com.moodi.task.sate.search.UiEffect
import com.moodi.task.sate.search.UiEvent
import com.moodi.task.screen.activity.ui.theme.TaskTheme
import com.moodi.task.screen.compose.DetailScreen
import com.moodi.task.screen.compose.HomeScreen
import com.moodi.task.viewmodel.RandomViewModel
import com.moodi.task.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

fun <A> String.fromJson(type: Class<A>): A {
    return Gson().fromJson(this, type)
}

fun <A> A.toJson(): String? {
    return Gson().toJson(this)
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail") {
        fun openDetail(appModel: GiphyAppModel) = "detail?model=${appModel.toJson()}"
    }
}

@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {

    private lateinit var navigationHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskTheme {
                navigationHostController = rememberNavController()
                SetupNavGraph(
                    navigationHostController
                )
            }
        }
    }
}

@Composable
private fun SetupNavGraph(
    navigationHostController: NavHostController
) {
    NavHost(
        navController = navigationHostController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            val searchViewModel = hiltViewModel<SearchViewModel>()
            val randomSearchViewModel = hiltViewModel<RandomViewModel>()

            val searchState = searchViewModel.dataState.collectAsState()
            val randomSate = randomSearchViewModel.dataState.collectAsState()

            LaunchedEffect(key1 = true) {
                searchViewModel.uiEffectState.collectLatest {
                    when (it) {
                        is UiEffect.NavigateToDetail -> {
                        }

                        is UiEffect.ShowSnackBar -> {}
                    }
                }
            }

            HomeScreen(
                searchState = searchState.value,
                randomState = randomSate.value,
                onPressedItem = {
                    navigationHostController.navigate(Screen.Detail.openDetail(it))
                },
                onQuerySearch = {
                    searchViewModel.onEvent(UiEvent.Search(it))
                },
                onSearchClearPress = {
                    searchViewModel.onEvent(UiEvent.ClearSearch)
                }
            )
        }
        composable(
            "detail?model={model}",
            arguments = listOf(
                navArgument("model") {
                    type = NavType.StringType
                }
            )
        ) {
            it.arguments
                ?.getString("model")
                ?.fromJson(GiphyAppModel::class.java)
                ?.let { model ->
                    DetailScreen(
                        model = model,
                        onNavUp = {
                            navigationHostController.popBackStack()
                        }
                    )
                }
        }
    }
}
