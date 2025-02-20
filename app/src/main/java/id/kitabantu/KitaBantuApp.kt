package id.kitabantu

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.kitabantu.ui.navigation.NavigationItem
import id.kitabantu.ui.navigation.Screen
import id.kitabantu.ui.screen.chat.ChatScreen
import id.kitabantu.ui.screen.detail.DetailScreen
import id.kitabantu.ui.screen.home.HomeScreen
import id.kitabantu.ui.screen.profile.ProfileScreen
import id.kitabantu.ui.theme.BluePylon

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
        bottomBar = {
            if (currentRoute in listOf(
                    Screen.Home.route,
                    Screen.Chat.route,
                    Screen.Profile.route
                )
            ) BottomBar(navController = navController, currentRoute = currentRoute)
        },
        floatingActionButton = {
          //detail screen
        },
        floatingActionButtonPosition = FabPosition.Center,

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
            composable(Screen.Chat.route) {
                ChatScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    currentRoute: String?
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = BluePylon,
        tonalElevation = 6.dp
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                selectedIcon = Icons.Default.Home,
                unselectedIcon = Icons.Outlined.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_chat),
                selectedIcon = Icons.Default.Chat,
                unselectedIcon = Icons.Outlined.Chat,
                screen = Screen.Chat
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                selectedIcon = Icons.Default.AccountCircle,
                unselectedIcon = Icons.Outlined.AccountCircle,
                screen = Screen.Profile
            ),
        )

        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.screen.route) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.secondary
                ),
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}