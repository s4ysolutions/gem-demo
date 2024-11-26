package solutions.s4y.jeleapps1.ui.viewmodels

import androidx.lifecycle.ViewModel
import solutions.s4y.jeleapps1.interactions.gcm.GCMService

class TokenViewModel(): ViewModel() {
    val tokenFlow = GCMService.tokenFlow
}