package id.kitabantu

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
import id.kitabantu.ui.screen.bookmark.BookmarkScreen
import id.kitabantu.ui.screen.chat.ChatScreen
import id.kitabantu.ui.screen.chat.JobChatScreen
import id.kitabantu.ui.screen.detail.DetailScreen
import id.kitabantu.ui.screen.home.HomeScreen
import id.kitabantu.ui.screen.login.LoginScreen
import id.kitabantu.ui.screen.profile.ProfileScreen
import id.kitabantu.ui.screen.register.RegisterScreen
import id.kitabantu.ui.theme.BluePylon

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KitaBantuApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),

    ) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        topBar = {
            when (currentRoute) {
                Screen.Bookmark.route -> {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.bookmark_menu),
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .width(230.dp)
                                    .padding(
                                        start = 8.dp,
                                        bottom = 2.dp,
                                        top = 4.dp
                                    )
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    navController.navigate(Screen.Profile.route)
                                },
                                modifier = modifier
                            ) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = modifier
                                )
                            }
                        },
                        actions = {
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
                Screen.Profile.route ->{
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = R.string.menu_profile),
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .width(230.dp)
                                    .padding(
                                        start = 8.dp,
                                        bottom = 2.dp,
                                        top = 4.dp
                                    )
                            )
                        },
                        navigationIcon = {
                        },
                        actions = {
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
            }

        },
        bottomBar = {
            if (currentRoute in listOf(
                    Screen.Home.route,
                    Screen.JobChat.route,
                    Screen.Profile.route
                )
            ) BottomBar(navController = navController, currentRoute = currentRoute)
        },
        floatingActionButton = {
            //detail screen
        },
        floatingActionButtonPosition = FabPosition.Center,

        ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.Login.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
            },

        ){
            composable(Screen.Login.route) {
                LoginScreen(
                    navigateToHome = {
                       navController.navigate(Screen.Home.route)
                    },
                    navigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    navigateToLogin = {
                    navController.navigate(Screen.Login.route)
                })
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { id ->
                        navController.navigate(Screen.DetailJob.createRoute(id))
                    }
                )
            }
            composable(
                route = Screen.DetailJob.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) {
                val jobId = it.arguments?.getLong("id") ?: -1L
                DetailScreen(
                    id = jobId,
                    navigateToHome = { navController.navigate(Screen.Home.route) },
                    navigateToDetail = { id ->
                        navController.navigate(Screen.DetailJob.createRoute(id))
                    },
                    navigateToChat = { id ->
                        navController.navigate(Screen.Chat.createRoute(id))
                    }
                )
            }
            composable(Screen.JobChat.route) {
                JobChatScreen(
                    navigateToChat = { id ->
                        navController.navigate(Screen.Chat.createRoute(id))
                    }
                )
            }
            composable(
                route = Screen.Chat.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) {
                ChatScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    navigateToBookmark = {
                        navController.navigate(Screen.Bookmark.route)
                    }
                )
            }
            composable(Screen.Bookmark.route) {
                BookmarkScreen(navigateToDetail = { id ->
                    navController.navigate(Screen.DetailJob.createRoute(id))
                })
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
                screen = Screen.JobChat
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