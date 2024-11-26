package solutions.s4y.jeleapps1.interactions.gcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import solutions.s4y.jeleapps1.interactions.Message

// This demo is not supposed to work with the custom GCM backend
// so it does not store the token anywhere
class GCMService : FirebaseMessagingService() {
    sealed class TokenState {
        data object Loading : TokenState()
        data class Success(val token: String) : TokenState()
        data class Error(val exception: Exception) : TokenState()
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(
            TAG,
            "onMessageReceived: ${remoteMessage.notification?.title ?: "no title"} ${remoteMessage.data}"
        )
        val success = _messageFlow.tryEmit(Message(remoteMessage))
        Log.d(
            TAG,
            "onMessageReceived: tryEmit success: $success to ${_messageFlow.subscriptionCount.value} subscribers"
        )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: $token")
        val success = _tokenFlow.tryEmit(TokenState.Success(token))
        Log.d(
            TAG,
            "onNewToken: tryEmit success: $success to ${_tokenFlow.subscriptionCount.value} subscribers"
        )
    }

    companion object {
        private val TAG = GCMService::class.java.simpleName

        private val _tokenFlow = MutableStateFlow<TokenState>(TokenState.Loading)
        val tokenFlow: StateFlow<TokenState> = _tokenFlow

        private val _messageFlow = MutableSharedFlow<Message>(
            extraBufferCapacity = 10,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
        val messageFlow: SharedFlow<Message> = _messageFlow

        fun requestNewToken() {
            // it is supposed to be called only once and there's no need to remove the listener
            // - no remove listener in the official documentation
            // - confirmed by ChatGPT
            FirebaseMessaging.getInstance().token.addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Log.d(TAG, "requestNewToken: ${result.result}")
                    val success = _tokenFlow.tryEmit(TokenState.Success(result.result))
                    Log.d(
                        TAG,
                        "requestNewToken: tryEmit success: $success to ${_tokenFlow.subscriptionCount.value} subscribers"
                    )
                } else {
                    Log.e(TAG, "requestNewToken: ${result.exception}")
                    val success = _tokenFlow.tryEmit(
                        TokenState.Error(
                            result.exception ?: Exception("Unknown error occurred")
                        )
                    )
                    Log.d(
                        TAG,
                        "requestNewToken: tryEmit success: $success to ${_tokenFlow.subscriptionCount.value} subscribers"
                    )
                }
            }
        }

        fun subscribeToTopic(topic: String = "test") {
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener { result ->
                    Log.d(TAG, "subscribeToTopic[topic:test]: ${result.isSuccessful}")
                }
        }

        fun sendNotification(title: String, body: String, data: Map<String, String>) {
            _messageFlow.tryEmit(Message(Message.Notification(title, body), data))
        }
    }
}