package id.kitabantu.ui.screen.chat

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.kitabantu.R

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val uiState = viewModel.chat.collectAsStateWithLifecycle()
        .value

    when (uiState) {
        ChatUiState.Loading -> {
            Box(
                modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ChatUiState.Error -> {
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

        is ChatUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .height(48.dp)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
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
                            text = "HR ${uiState.chat.job.company}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Text(
                            text = uiState.chat.job.company,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            maxLines = 1,
                        )
                    }
                }

                HorizontalDivider()
                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    val messages = uiState.chat.messages
                    items(messages) { message ->
                        ChatBubble(message = message)
                    }
                }

                ChatBox(
                    onSendChatClickListener = { message ->
                        viewModel.sendMessage(uiState.chat.job, message)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ChatBubble(
    message: String,
    modifier: Modifier = Modifier
) {
    val isFromMe = true

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = if (isFromMe) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isFromMe) 16.dp else 0.dp,
                        bottomEnd = if (isFromMe) 0.dp else 16.dp
                    )
                )
                .background(if (isFromMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp)
        ) {
            Text(
                text = message,
                color = if (isFromMe) Color.White else MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun ChatBox(
    onSendChatClickListener: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = chatBoxValue,
            onValueChange = { newText -> chatBoxValue = newText },
            placeholder = { Text(text = "Type a message...") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .weight(1f)
                .background(Color.Transparent)
        )
        Spacer(
            modifier = Modifier
                .width(8.dp)
        )
        IconButton(
            onClick = {
                val text = chatBoxValue.text.trim()
                if (text.isNotEmpty()) {
                    onSendChatClickListener(text)
                    chatBoxValue = TextFieldValue("") // Clear input after sending
                }
            },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send Message",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}