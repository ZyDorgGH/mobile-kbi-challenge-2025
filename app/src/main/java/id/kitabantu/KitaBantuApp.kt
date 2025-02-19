package id.kitabantu

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.kitabantu.ui.navigation.Screen
import id.kitabantu.ui.screen.detail.DetailScreen
import id.kitabantu.ui.screen.home.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KitaBantuApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),

){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold (
        modifier = modifier,
        topBar = {

        },
        floatingActionButton = {
          //detail screen
        },
        floatingActionButtonPosition = FabPosition.Center

    ) {innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.Home.route,
            enterTransition = {
                // Transisi masuk: geser ke kanan
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
            },
            exitTransition = {
                // Transisi keluar: geser ke kiri
                slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            },
            popEnterTransition = {
                // Transisi masuk saat pop: geser ke kiri
                slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
            },
            popExitTransition = {
                // Transisi keluar saat pop: geser ke kanan
                slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
            },

        ){
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = {title ->
                        navController.navigate(Screen.DetailJob.createRoute(title))
                    }
                )
            }
            composable(
                route = Screen.DetailJob.route,
                arguments = listOf(navArgument("title") { type = NavType.StringType })
            ) {
                val jobTitle = it.arguments?.getString("title") ?: ""
                DetailScreen(
                    title = jobTitle,
                    navigateToHome = { navController.navigate(Screen.Home.route) },
                    navigateToDetail = {title ->
                        navController.navigate(Screen.DetailJob.createRoute(title))
                    }
                )

            }
        }
    }
}