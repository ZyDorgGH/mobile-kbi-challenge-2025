package id.kitabantu.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navigateToBookmark: () -> Unit
){
    Column {
        Text(text = "Profile Screen")
        Button(onClick = {
            navigateToBookmark()
        }) {
            Text(text = "Bookmark Screen")
        }
    }

}