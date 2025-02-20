package id.kitabantu.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailJob : Screen("home/{title}") {
        fun createRoute(title: String) = "home/$title"
    }
    object Chat : Screen("chat")
    object Profile : Screen("profile")
}