package solutions.s4y.jeleapps1.ui.composables.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import solutions.s4y.jeleapps1.ui.composables.gcm.MessagesView
import solutions.s4y.jeleapps1.ui.composables.gcm.TokenView

@Composable
fun MainScreen() {
    Column {
        TokenView(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        /*
        SendView(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
         */
        MessagesView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        )
    }

}


@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}