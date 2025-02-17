package id.kitabantu.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailJob : Screen("home/{id}") {
        fun createRoute(id: Int) = "home/$id"
    }
    object Profile : Screen("profile")
}