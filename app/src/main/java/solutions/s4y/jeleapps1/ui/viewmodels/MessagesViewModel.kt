package solutions.s4y.jeleapps1.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import solutions.s4y.jeleapps1.interactions.Message
import solutions.s4y.jeleapps1.interactions.gcm.GCMService

class MessagesViewModel : ViewModel() {
    val messagesFlow =
        GCMService.messageFlow.scan(emptyList<Message>()) { accumulated, message ->
            Log.d("MessagesViewModel", "message: ${message.notification?.title}")
            accumulated + message
        }.onEach {
            Log.d("MessagesViewModel", "messages onEach: ${it.size}")
        }
}