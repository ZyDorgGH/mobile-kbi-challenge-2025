package id.kitabantu.ui.screen.detail

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import id.kitabantu.R
import id.kitabantu.data.remote.converter.formatToWIB
import id.kitabantu.data.remote.converter.toDisplayString
import id.kitabantu.model.Job
import id.kitabantu.ui.JobsUiState
import id.kitabantu.ui.JobsViewModel
import id.kitabantu.ui.theme.GreyLight
import id.kitabantu.ui.theme.blackWithTransparency
import kotlin.text.Typography.bullet

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    title: String,
    navigateToHome: () -> Unit,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JobsViewModel = hiltViewModel(),

    ) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val detailDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    viewModel.jobsUiState.collectAsStateWithLifecycle(initialValue = JobsUiState.Loading).value.let { uiState ->
        when (uiState) {
            is JobsUiState.Loading -> {
                Box(
                    modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is JobsUiState.Success -> {
                val jobs = uiState.jobs
                val job = uiState.jobs.find {
                    it.title == title
                }
                Column(
                    modifier
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                        .fillMaxHeight()
                        .background(GreyLight)
                ) {
                    if (job != null) {
                        LargeTopAppBar(
                            modifier = modifier,
                            title = {
                                Column(
                                    modifier = modifier
                                        .background(Color.White)
                                        .clickable {

                                        }
                                ) {
                                    if (scrollBehavior.state.collapsedFraction < 0.5) {
                                        AsyncImage(
                                            model = job.companyLogoUrl,
                                            contentDescription = null,
                                            modifier = modifier
                                                .padding(start = 8.dp)
                                                .width(150.dp)
                                                .height(48.dp),
                                            contentScale = ContentScale.Inside,
                                            alignment = Alignment.CenterStart
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (scrollBehavior.state.collapsedFraction >= 0.5) {
                                            IconButton(
                                                onClick = {
                                                    navigateToHome()
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
                                        }

                                        Column(
                                            modifier = modifier,
                                        ) {
                                            if (scrollBehavior.state.collapsedFraction >= 0.5){
                                                Text(
                                                    text = job.title,
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = Color.Black,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    modifier = Modifier
                                                        .width(230.dp)
                                                        .padding(
                                                            start = 8.dp,
                                                            bottom = 2.dp,
                                                            top = 8.dp
                                                        )
                                                )
                                            } else {
                                                Text(
                                                    text = job.title,
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = Color.Black,
                                                    maxLines = 2,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(
                                                            start = 8.dp,
                                                            bottom = 2.dp,
                                                            top = 8.dp
                                                        )
                                                )
                                            }

                                            Text(
                                                text = job.company,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = Color.DarkGray,
                                                modifier = Modifier.padding(
                                                    start = 8.dp,
                                                    bottom = 2.dp
                                                )
                                            )
                                            if (scrollBehavior.state.collapsedFraction < 0.5) {
                                                Text(
                                                    text = job.excerpt,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = Color.Black,
                                                    maxLines = 2,
                                                    modifier = Modifier.padding(start = 8.dp, end = 16.dp)
                                                )
                                            }
                                        }
                                    }
                                }

                            },
                            expandedHeight = 220.dp,
                            colors = TopAppBarColors(
                                containerColor = Color.White,
                                scrolledContainerColor = Color.White,
                                navigationIconContentColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = Color.White,
                                actionIconContentColor = MaterialTheme.colorScheme.primary
                            ),
                            navigationIcon = {
                                if (scrollBehavior.state.collapsedFraction < 0.5) {
                                    IconButton(
                                        onClick = {
                                            navigateToHome()
                                        },
                                        modifier = modifier.padding(end = 8.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.ArrowBack,
                                            contentDescription = "Back",
                                            modifier
                                        )
                                    }
                                }
                            },
                            actions = {
                                var isBookmarked by remember { mutableStateOf(viewModel.isBookmark(job)) }
                                IconButton(
                                    onClick = {
                                        if (isBookmarked){
                                            viewModel.removeBookmark(job)
                                        } else {
                                            viewModel.addBookmark(job)
                                        }
                                        isBookmarked = viewModel.isBookmark(job)
                                        Log.e("isBookmark", "$isBookmarked")
                                    },
                                    modifier = modifier
                                ) {
                                    if (isBookmarked) {
                                        Icon(
                                            imageVector = Icons.Default.Bookmark,
                                            contentDescription = "Bookmark",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Outlined.BookmarkBorder,
                                            contentDescription = "Bookmark",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                                IconButton(
                                    onClick = {
                                        val intent = Intent()
                                        intent.action = Intent.ACTION_SEND
                                        intent.putExtra(
                                            Intent.EXTRA_TEXT,
                                            "${job.title}\nhttps://example.job.com/jobs\nFind a Job in Bantu"
                                        )
                                        intent.type = "text/plain"
                                        ContextCompat.startActivity(
                                            context,
                                            Intent.createChooser(intent, "Share di:"),
                                            null
                                        )
                                    },
                                    modifier = modifier
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Share,
                                        contentDescription = "Bookmark",
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                }
                            },
                            scrollBehavior = scrollBehavior,
                        )
                        DetailContent(
                            job = job,
                            jobs = jobs,
                            title = title,
                            navigateToDetail
                        )
                    }

                }

            }

            is JobsUiState.Error -> {
                val errorMessage = uiState.message
                if (detailDialog.value) {
                    BasicAlertDialog(
                        onDismissRequest = { }
                    ) {
                        Surface(
                            modifier = modifier
                                .wrapContentWidth()
                                .wrapContentHeight(),
                            shape = MaterialTheme.shapes.large,
                            tonalElevation = AlertDialogDefaults.TonalElevation
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Warning,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = stringResource(R.string.connection_problem),
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = errorMessage,
                                    color = MaterialTheme.colorScheme.error
                                )
                                Spacer(modifier = modifier.height(24.dp))
                                TextButton(
                                    onClick = { detailDialog.value = false },
                                    modifier = modifier.align(Alignment.End)
                                ) {
                                    Text(stringResource(R.string.back_to_home), color = Color.Black)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DetailContent(
    job: Job,
    jobs: List<Job>,
    title: String,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier
        ) {
            val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
            Row(
                modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Column(
                    modifier
                        .weight(1f)
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.padding(
                            start = 24.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 2.dp
                        )
                    ){
                        Icon(
                            imageVector = Icons.Outlined.WatchLater,
                            contentDescription = "time",
                            modifier
                                .padding(4.dp)
                                .size(18.dp),
                            tint = MaterialTheme.colorScheme.primary

                        )
                        Text(
                            text = stringResource(R.string.posted_at),
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )
                    }

                    Text(
                        text = "${formatToWIB(job.published)} WIB",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 30.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.padding(
                            start = 24.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 2.dp
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MonetizationOn,
                            contentDescription = "salary",
                            modifier
                                .padding(4.dp)
                                .size(18.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = stringResource(R.string.salary),
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )
                    }
                    Text(
                        text = "${job.salaryCurrency} ${job.salary} /month",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 30.dp, end = 16.dp, bottom = 16.dp)
                    )
                }
                Column(
                    modifier
                        .weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.padding(
                            end = 16.dp, top = 16.dp, bottom = 2.dp
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.WorkOutline,
                            contentDescription = "job_type",
                            modifier
                                .padding(4.dp)
                                .size(18.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = stringResource(R.string.job_type),
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )
                    }
                    Text(
                        text = job.type.toDisplayString(),
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 6.dp, end = 24.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.padding(
                            end = 16.dp, top = 16.dp, bottom = 2.dp
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "location",
                            modifier
                                .padding(4.dp)
                                .size(18.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = stringResource(R.string.job_location),
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )
                    }
                    Text(
                        text = job.location,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 6.dp, end = 16.dp, bottom = 16.dp)
                    )

                }
            }
            Column(
                modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = stringResource(R.string.qualification),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        start = 24.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 8.dp
                    )
                )

                Text(
                    buildAnnotatedString {
                        job.description.forEach {
                            withStyle(style = paragraphStyle) {
                                append(bullet)
                                append("\t\t")
                                append(it)
                            }
                        }
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
                    maxLines = if (!expanded) 3 else Int.MAX_VALUE,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 28.dp, end = 32.dp, bottom = 16.dp)
                )

                if (job.description.size >= 4)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        TextButton(onClick = { expanded = !expanded }) {
                            Text(text = if (expanded) "Show less" else "Show more")
                        }
                    }
            }

        }
        Column(
            modifier
        ) {
            Text(
                text = stringResource(R.string.find_another_job),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 24.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
            )


            DetailList(
                jobs = jobs.filter {
                    it.title != title
                },
                navigateToDetail = navigateToDetail,
                modifier = modifier.height(706.dp)
            )
        }
    }
}

@Composable
private fun DetailList(
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
            DetailItem(
                job = job,
                navigateToDetail
            )
        }
    }
}

@Composable
private fun DetailItem(
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
