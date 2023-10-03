package com.moodi.task.screen.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.moodi.task.data.local.GiphyAppModel
import com.moodi.task.screen.activity.ui.theme.TaskTheme
import com.moodi.task.screen.compose.DetailScreen
import com.moodi.task.screen.compose.HomeScreen
import com.moodi.task.ui.viewmodel.RandomViewModel
import com.moodi.task.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

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
                setupNavGraph(
                    navigationHostController
                )
            }
        }
    }

}

@Composable
private fun setupNavGraph(
    navigationHostController: NavHostController
) {
    NavHost(
        navController = navigationHostController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            val searchViewModel = hiltViewModel<SearchViewModel>()
            val randomSearchViewModel = hiltViewModel<RandomViewModel>()
            HomeScreen(
                searchViewModel = searchViewModel,
                randomViewModel = randomSearchViewModel,
                onPressedItem = {
                    navigationHostController.navigate(Screen.Detail.openDetail(it))
                }
            )
        }
        composable(
            "detail?model={model}",
            arguments = listOf(navArgument("model") {
                type = NavType.StringType
            })
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


