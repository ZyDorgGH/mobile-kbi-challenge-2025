package id.kitabantu.ui.screen.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SelectableChipElevation
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import id.kitabantu.R
import id.kitabantu.data.remote.converter.toDisplayString
import id.kitabantu.data.remote.converter.toJobSort
import id.kitabantu.data.remote.converter.toJobType
import id.kitabantu.model.DummyData
import id.kitabantu.model.Job
import id.kitabantu.ui.JobsUiState
import id.kitabantu.ui.JobsViewModel
import id.kitabantu.ui.theme.GreyLight
import id.kitabantu.ui.theme.blackWithTransparency
import java.text.NumberFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    viewModel: JobsViewModel = hiltViewModel()

) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var isSearchVisible by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val homeDialog = remember { mutableStateOf(false) }

    LaunchedEffect(searchQuery, isSearchVisible) {
        if (!isSearchVisible){
            viewModel.setSearchQuery(searchQuery)
        }
    }

    viewModel.jobsUiState.collectAsState(initial = JobsUiState.Loading).value.let { uiState ->
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
                if (isSearchVisible) {
                    CustomSearchView(
                        isSearchVisible = isSearchVisible,
                        onSearchVisibilityChange = { isSearchVisible = it },
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it }
                    )
                } else {
                    Column(
                        modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                    ) {
                        val selectedFilters = remember { mutableStateOf<String?>(null) }
                        val selectedSort = remember { mutableStateOf("New Added") }
                        val categoryList = listOf("Full Time", "Contract", "Part Time", "Internship")
                        val sortList = listOf("New Added", "Salary")

                        if (!isSearchVisible) {
                            Surface(shadowElevation = 3.dp) {
                                MediumTopAppBar(
                                    modifier = modifier,
                                    title = {
                                        LazyRow {
                                            items(categoryList) { category ->
                                                ElevatedFilterChip(
                                                    selected = selectedFilters.value == category,
                                                    onClick = {
                                                        if (selectedFilters.value == category) {
                                                            selectedFilters.value = null

                                                            viewModel.setCategories(emptySet())
                                                        } else {
                                                            selectedFilters.value = category
                                                            Log.e(
                                                                "test",
                                                                category.toJobType().toString()
                                                            )
                                                            viewModel.setCategories(setOf(category.toJobType()))
                                                        }
                                                    },
                                                    elevation = SelectableChipElevation(
                                                        elevation = 0.dp,
                                                        pressedElevation = 0.dp,
                                                        focusedElevation = 0.dp,
                                                        hoveredElevation = 0.dp,
                                                        draggedElevation = 0.dp,
                                                        disabledElevation = 0.dp

                                                    ),
                                                    label = {
                                                        Text(
                                                            text = category,
                                                            Modifier,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    },
                                                    leadingIcon = {
                                                        if (selectedFilters.value == category) Icon(
                                                            Icons.Filled.Check,
                                                            contentDescription = null,
                                                            tint = MaterialTheme.colorScheme.secondary
                                                        )
                                                    },
                                                    colors = FilterChipDefaults.filterChipColors(
                                                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                                                        selectedLabelColor = MaterialTheme.colorScheme.secondary,
                                                        containerColor = blackWithTransparency,
                                                        labelColor = MaterialTheme.colorScheme.primary
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 0.dp,
                                                        end = 8.dp
                                                    )
                                                )
                                            }
                                        }

                                    },
                                    colors = TopAppBarColors(
                                        containerColor = Color.White,
                                        scrolledContainerColor = Color.White,
                                        navigationIconContentColor = MaterialTheme.colorScheme.primary,
                                        titleContentColor = Color.White,
                                        actionIconContentColor = MaterialTheme.colorScheme.primary
                                    ),
                                    navigationIcon = {

                                    },
                                    actions = {
                                        if (scrollBehavior.state.collapsedFraction < 0.5) {
                                            Box(
                                                modifier = Modifier.wrapContentSize(Alignment.TopEnd),
                                                contentAlignment = Alignment.BottomCenter

                                            ) {
                                                Row(
                                                    modifier
                                                        .clip(RoundedCornerShape(12.dp))
                                                        .clickable {
                                                            expanded = true
                                                        }
                                                        .padding(4.dp)
                                                        .height(32.dp),
                                                    verticalAlignment = Alignment.Bottom
                                                ) {
                                                    Text(
                                                        text = selectedSort.value,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 13.sp,

                                                        color = MaterialTheme.colorScheme.primary,
                                                        modifier = modifier
                                                            .padding(start = 4.dp)
                                                            .align(Alignment.CenterVertically)
                                                    )
                                                    if (expanded) {
                                                        Icon(
                                                            imageVector = Icons.Default.KeyboardArrowUp,
                                                            contentDescription = "sort",
                                                            modifier = modifier
                                                                .padding(4.dp)
                                                                .size(22.dp)
                                                                .align(Alignment.CenterVertically)
                                                        )
                                                    } else {
                                                        Icon(
                                                            imageVector = Icons.Default.KeyboardArrowDown,
                                                            contentDescription = "sort",
                                                            modifier = modifier
                                                                .padding(4.dp)
                                                                .size(22.dp)
                                                                .align(Alignment.CenterVertically)
                                                        )
                                                    }
                                                }
                                                DropdownMenu(
                                                    modifier = modifier.background(MaterialTheme.colorScheme.primary),
                                                    expanded = expanded,
                                                    onDismissRequest = { expanded = false }) {
                                                    sortList.map { sort ->
                                                        DropdownMenuItem(
                                                            text = {
                                                                Text(
                                                                    sort,
                                                                    color = MaterialTheme.colorScheme.secondary
                                                                )
                                                            },
                                                            onClick = {
                                                                if (selectedSort.value != sort) {
                                                                    selectedSort.value = sort
                                                                    viewModel.setSort(sort.toJobSort())

                                                                }
                                                            },
                                                            leadingIcon = {
                                                                if (selectedSort.value == sort) {
                                                                    Icon(
                                                                        imageVector = Icons.Default.Check,
                                                                        contentDescription = "Selected",
                                                                        tint = MaterialTheme.colorScheme.secondary
                                                                    )
                                                                }
                                                            },
                                                        )
                                                    }
                                                }
                                            }

                                            IconButton(
                                                onClick = { isSearchVisible = true },
                                                modifier = modifier.padding(end = 8.dp)
                                            ) {
                                                Icon(
                                                    Icons.Default.Search,
                                                    contentDescription = "Search",
                                                    modifier
                                                        .size(24.dp)

                                                )
                                            }


                                        }
                                    },
                                    scrollBehavior = scrollBehavior,
                                )
                            }
                        }
                        HomeList(
                            jobs = jobs,
                            navigateToDetail = navigateToDetail
                        )
                    }
                }

            }

            is JobsUiState.Error -> {
                val errorMessage = uiState.message
                Log.e("JobsUiState.Error", errorMessage)
                homeDialog.value = true
                if (homeDialog.value) {
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
                                    onClick = { homeDialog.value = false },
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

@Composable
private fun HomeList(
    jobs: List<Job>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier.background(GreyLight),
        contentPadding = PaddingValues(horizontal = 8.dp),
        userScrollEnabled = true
    ) {
        items(jobs) { job ->
            HomeItem(
                job = job,
                navigateToDetail
            )
        }
    }
}

@Composable
private fun HomeItem(
    job: Job,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .clickable {
                Log.e("job.id", job.id.toString())
                navigateToDetail(job.id)
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
                        color = Color.Black,
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
                    val numberFormat = NumberFormat.getInstance(Locale("id", "ID"))
                    val jobSalary = numberFormat.format(job.salary)
                    Text(
                        text = "${job.salaryCurrency} $jobSalary /month",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray,
                        modifier = Modifier
                    )
                }
            }

        }


    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchView(
    isSearchVisible: Boolean,
    onSearchVisibilityChange: (Boolean) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val KeywordData = DummyData.keyword

    val filteredSuggestions = KeywordData.filter {
        it.keyword.contains(searchQuery, ignoreCase = true)
    }


    SearchBar(
        query = searchQuery,
        onQueryChange = {
            onSearchQueryChange(it)
        },
        onSearch = { onSearchVisibilityChange(false) },
        active = isSearchVisible,
        onActiveChange = {
            onSearchQueryChange("")
            onSearchVisibilityChange(it)
        },
        placeholder = {
            Text(text = "Search Job")
        },
        leadingIcon = {
            IconButton(
                onClick = {
                    onSearchVisibilityChange(false)
                },
                modifier.padding(end = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Button",
                )
            }
        },
        trailingIcon = {
            if (isSearchVisible) {
                Icon(
                    modifier =
                    Modifier.clickable {
                        if (searchQuery.isNotEmpty()) {
                            onSearchQueryChange("")
                        } else {
                            onSearchVisibilityChange(false)
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon"
                )
            }
        },

        ) {
        if (searchQuery.isNotEmpty() && filteredSuggestions.isNotEmpty()) {
            LazyColumn {
                items(filteredSuggestions) { suggestion ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSearchQueryChange(suggestion.keyword)
                                onSearchVisibilityChange(false)
                            }
                            .padding(16.dp)
                            .height(IntrinsicSize.Min)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(end = 16.dp)
                        )
                        Text(
                            text = suggestion.keyword,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}
