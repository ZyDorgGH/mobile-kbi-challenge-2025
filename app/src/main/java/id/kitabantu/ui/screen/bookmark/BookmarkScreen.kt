package id.kitabantu.ui.screen.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import id.kitabantu.data.remote.converter.toDisplayString
import id.kitabantu.model.Job
import id.kitabantu.ui.theme.GreyLight
import id.kitabantu.ui.theme.blackWithTransparency

@Composable
fun BookmarkScreen(
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookmarkViewModel = hiltViewModel()
){

    viewModel.bookmarkUiState.collectAsStateWithLifecycle(initialValue = BookmarkUiState.Loading).value.let { uiState ->
        when(uiState){
            is BookmarkUiState.Loading -> {
                Box(
                    modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is BookmarkUiState.Success -> {

                Column(
                    modifier
                        .fillMaxHeight()
                        .background(GreyLight)
                ) {
                    val jobs = uiState.jobs
                    if (jobs.isNotEmpty()){
                        BookmarkList(
                            jobs = jobs,
                            navigateToDetail = navigateToDetail
                        )
                    } else {
                        EmptyOrErrorScreen(
                            state = ScreenState.Empty,
                            onRetry = {}
                        )
                    }
                }
            }
            is BookmarkUiState.Error -> {
                EmptyOrErrorScreen(
                    state = ScreenState.Error,
                    onRetry = {}
                )

            }
        }
    }

}

@Composable
private fun BookmarkList(
    jobs: List<Job>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier.background(GreyLight),
        contentPadding = PaddingValues(horizontal = 8.dp),
        userScrollEnabled = false
    ) {
        items(jobs) { job ->
            BookmarkItem(
                job = job,
                navigateToDetail
            )
        }
    }
}

@Composable
private fun BookmarkItem(
    job: Job,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .clickable {
                navigateToDetail(job.title)
            }

    ) {
        AsyncImage(
            model = job.companyLogoUrl,
            contentDescription = null,
            modifier = modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                .width(150.dp)
                .height(48.dp),
            contentScale = ContentScale.Inside,
            alignment = Alignment.CenterStart
        )
        Box(Modifier.fillMaxWidth()) {
            Column(
                modifier = modifier
                    .align(Alignment.CenterStart)
                    .width(230.dp),

                ) {
                Text(
                    text = job.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    maxLines = 2,
                    modifier = Modifier.padding(start = 16.dp, bottom = 2.dp)
                )
                Text(
                    text = job.company,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                )
            }
            Column(
                modifier.align(Alignment.BottomEnd)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 12.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(blackWithTransparency),

                    ) {
                    Text(
                        text = job.type.toDisplayString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.padding(start = 8.dp, end = 8.dp, top = 0.dp)
                    )
                }

                Text(
                    text = job.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    maxLines = 1,
                    textAlign = TextAlign.End,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width(160.dp)
                        .align(Alignment.End)
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                )

                Row(
                    Modifier
                        .padding(top = 4.dp, end = 16.dp, bottom = 16.dp)
                        .align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${job.salaryCurrency} ${job.salary} /month",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray,
                        modifier = Modifier
                    )
                }
            }

        }
    }
}

enum class ScreenState {
    Empty, Error
}

@Composable
fun EmptyOrErrorScreen(
    state: ScreenState,
    onRetry: () -> Unit
) {
    val (icon, title, description) = when (state) {
        ScreenState.Empty -> Triple(
            Icons.Default.Inbox,
            "Bookmark Kosong",
            "Anda belum menambahkan bookmark apa pun."
        )
        ScreenState.Error -> Triple(
            Icons.Default.SignalWifiConnectedNoInternet4,
            "Gagal Memuat Data",
            "Terjadi kesalahan saat memuat data. Silakan coba lagi."
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Background lingkaran untuk ikon
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary

        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        )
        if (state == ScreenState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text(text = "Coba Lagi")
            }
        }
    }
}

