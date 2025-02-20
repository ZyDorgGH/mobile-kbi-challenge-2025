@file:OptIn(ExperimentalMaterial3Api::class)

package id.kitabantu.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import id.kitabantu.R

@Composable
fun ProfileScreen(
    navigateToBookmark: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Hisyam Fausta",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "hisyamfausta@gmail.com",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(30.dp))

            Divider()

            Spacer(modifier = Modifier.height(10.dp))


            Column {
                Button(onClick = { navigateToBookmark() }) {
                    Text(text = "Bookmark Screen")
                }
            }
        }
    }
}
