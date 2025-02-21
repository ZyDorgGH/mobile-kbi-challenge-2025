package id.kitabantu.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailJob : Screen("home/{id}") {
        fun createRoute(id: Long) = "home/$id"
    }
    object JobChat : Screen("chat")
    object Chat : Screen("chat/{id}"){
        fun createRoute(id: Long) = "chat/$id"
    }
    object Profile : Screen("profile")
    object Bookmark : Screen("bookmark")

    object Login : Screen("login")

    object Register : Screen("register")
}