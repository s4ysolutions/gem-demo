package solutions.s4y.jeleapps1.ui.viewmodels

import androidx.lifecycle.ViewModel
import solutions.s4y.jeleapps1.interactions.gcm.GCMService

class SendViewModel() : ViewModel() {
    fun sendToItSelf() {
        GCMService.sendNotification(
            "title " + System.currentTimeMillis(),
            "body " + System.currentTimeMillis(),
            emptyMap()
        )
    }
}