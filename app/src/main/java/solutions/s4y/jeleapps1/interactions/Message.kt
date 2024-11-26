package solutions.s4y.jeleapps1.interactions

import com.google.firebase.messaging.RemoteMessage

data class Message(val notification: Notification?, val data: Map<String, String>) {
    data class Notification(val title: String?, val body: String?)

    constructor(remoteMessage: RemoteMessage) : this(
        Notification(
            remoteMessage.notification?.title,
            remoteMessage.notification?.body
        ), remoteMessage.data
    )
}