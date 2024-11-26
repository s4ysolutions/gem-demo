package solutions.s4y.jeleapps1.ui.composables.gcm

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import solutions.s4y.jeleapps1.ui.viewmodels.SendViewModel

@Composable
fun SendView(viewModel: SendViewModel = viewModel(), modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        TextButton(onClick = {
            viewModel.sendToItSelf()
        }) { Text("Send (Fake)") }
    }
}

@Preview
@Composable
fun SendViewPreview() {
    SendView()
}