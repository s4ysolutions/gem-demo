package solutions.s4y.jeleapps1.ui.composables.gcm

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import solutions.s4y.jeleapps1.ui.viewmodels.MessagesViewModel

@Composable
fun MessagesView(viewModel: MessagesViewModel = viewModel(), modifier: Modifier = Modifier) {
    val messages by viewModel.messagesFlow.collectAsStateWithLifecycle(emptyList())
    Log.d("MessageView", "messages: ${messages.size}")

    Column(modifier = modifier) {
        Text("Messages:", style = MaterialTheme.typography.titleMedium)
        messages.forEach { message ->
            Card(modifier = Modifier.padding(top = 8.dp).fillMaxWidth()) {
                Column (modifier = Modifier.padding(8.dp)) {
                    Text(
                        message.notification?.title ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        message.notification?.body ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text("Data:", style = MaterialTheme.typography.labelMedium)
                    message.data.forEach { (key, value) ->
                        Row {
                            Text(
                                key,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                value,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MessagesViewPreview() {
    MessagesView()
}
