package id.kitabantu.ui.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.kitabantu.R
import id.kitabantu.model.JobChat

@Composable
fun JobChatScreen(
    navigateToChat: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JobChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.jobChats.collectAsStateWithLifecycle()
    ChatPage(
        uiState = uiState,
        navigateToChat = navigateToChat,
        modifier = modifier,
    )
}

@Composable
fun ChatPage(
    uiState: JobChatUiState,
    navigateToChat: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        JobChatUiState.Loading -> {
            Box(
                modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is JobChatUiState.Error -> {
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
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        is JobChatUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {
                if (uiState.jobChats.isNotEmpty()) {
                    LazyColumn {
                        val chats = uiState.jobChats
                        items(chats) { chat ->
                            JobChatItem(
                                chat = chat,
                                navigateToChat = navigateToChat,
                            )
                            HorizontalDivider()
                        }
                    }
                } else {
                    EmptyOrErrorChatScreen(
                        state = ChatScreenState.EMPTY,
                        onRetry = {}
                    )
                }
            }
        }
    }
}

@Composable
private fun JobChatItem(
    chat: JobChat,
    navigateToChat: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { navigateToChat(chat.job.id) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "HR Company Profile Picture",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "HR ${chat.job.company}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = chat.messages.lastOrNull() ?: "No messages yet",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

enum class ChatScreenState {
    EMPTY, ERROR
}

@Composable
fun EmptyOrErrorChatScreen(
    state: ChatScreenState,
    onRetry: () -> Unit
) {
    val (icon, title, description) = when (state) {
        ChatScreenState.EMPTY -> Triple(
            Icons.Default.Chat,
            "No Chat Yet",
            "You haven't added any bookmarks yet."
        )
        ChatScreenState.ERROR -> Triple(
            Icons.Default.SignalWifiConnectedNoInternet4,
            "Failed to Load Data",
            "An error occurred while loading data. Please try again."
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
        if (state == ChatScreenState.ERROR) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text(text = stringResource(R.string.try_again))
            }
        }
    }
}