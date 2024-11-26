package solutions.s4y.jeleapps1.ui.composables.gcm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import solutions.s4y.jeleapps1.interactions.gcm.GCMService.TokenState
import solutions.s4y.jeleapps1.ui.viewmodels.TokenViewModel

@Composable
fun TokenView(viewModel: TokenViewModel = viewModel(), modifier: Modifier = Modifier) {
    val clipboardManager = LocalClipboardManager.current
    Box(modifier = modifier) {
        when (val token = viewModel.tokenFlow.collectAsStateWithLifecycle().value) {
            is TokenState.Loading -> Text("Waiting for token...")
            is TokenState.Success -> Column(modifier = Modifier.wrapContentHeight()) {
                val (deviceToken, tail) = token.token.split(':', limit = 2)
                    .let { (it.firstOrNull() ?: "Unknown") to it.getOrNull(1).orEmpty() }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Device token: $deviceToken",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium
                    )
                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString(deviceToken))
                    }, modifier = Modifier.width(48.dp)) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy")
                    }
                }
                //Text(tail)
            }

            is TokenState.Error -> Text("Error: ${token.exception}")
        }
    }
}

@Preview
@Composable
fun TokenViewPreview() {
    TokenView()
}