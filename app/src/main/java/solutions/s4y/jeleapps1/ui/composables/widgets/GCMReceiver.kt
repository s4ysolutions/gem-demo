package solutions.s4y.jeleapps1.ui.composables.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GCMReceiver(modifier: Modifier = Modifier) {
    Text("Hello Android!")
}

@Preview
@Composable
fun ReceiverPreview() {
    GCMReceiver()
}