package id.kitabantu.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailJob : Screen("home/{id}") {
        fun createRoute(id: Long) = "home/$id"
    }
    object Chat : Screen("chat")
    object Profile : Screen("profile")
    object Bookmark : Screen("bookmark")
}