# Google Cloud Messaging demo

## Interaction layer

- [GCMService](https://github.com/s4ysolutions/gem-demo/blob/main/app/src/main/java/solutions/s4y/jeleapps1/interactions/gcm/GCMService.kt) - [converts](https://github.com/s4ysolutions/gem-demo/blob/e8df926c83e89c1580ad83efdfeadc5dfcf5c9a4/app/src/main/java/solutions/s4y/jeleapps1/interactions/gcm/GCMService.kt#L27C1-L48C6) GCM platform-specific API to platform-indepndent Flow<Message> API
In this variant the API exposed as static methods for sake of simplicity:

  - [val tokenFlow: StateFlow<TokenState>](https://github.com/s4ysolutions/gem-demo/blob/e8df926c83e89c1580ad83efdfeadc5dfcf5c9a4/app/src/main/java/solutions/s4y/jeleapps1/interactions/gcm/GCMService.kt#L54)
  - [val messageFlow: SharedFlow<Message>](https://github.com/s4ysolutions/gem-demo/blob/e8df926c83e89c1580ad83efdfeadc5dfcf5c9a4/app/src/main/java/solutions/s4y/jeleapps1/interactions/gcm/GCMService.kt#L60C8-L60C46)
  - [fun requestNewToken(): Unit](https://github.com/s4ysolutions/gem-demo/blob/e8df926c83e89c1580ad83efdfeadc5dfcf5c9a4/app/src/main/java/solutions/s4y/jeleapps1/interactions/gcm/GCMService.kt#L62C9-L62C30)

## Use cases layer

 ViewModels hide from UI Layer the interaction/business logic layer
 
 - [MessagesViewModel](https://github.com/s4ysolutions/gem-demo/blob/main/app/src/main/java/solutions/s4y/jeleapps1/ui/viewmodels/MessagesViewModel.kt) - Flow emits the list of all data notification messages received from last launch
 - [TokenViewModel](https://github.com/s4ysolutions/gem-demo/blob/main/app/src/main/java/solutions/s4y/jeleapps1/ui/viewmodels/TokenViewModel.kt) - Flow emits the GCM tokens as soon as they are received either new or updated from the GC backend

## UI Layer

 - [MainScreen](https://github.com/s4ysolutions/gem-demo/blob/main/app/src/main/java/solutions/s4y/jeleapps1/ui/composables/screens/MainScreen.kt) - Composable [to be put into scaffold](https://github.com/s4ysolutions/gem-demo/blob/e8df926c83e89c1580ad83efdfeadc5dfcf5c9a4/app/src/main/java/solutions/s4y/jeleapps1/MainActivity.kt#L35C1-L35C41)
and contains a column of (at least) 2 nested composables:
   - [TokenView](https://github.com/s4ysolutions/gem-demo/blob/main/app/src/main/java/solutions/s4y/jeleapps1/ui/composables/gcm/TokenView.kt)
   - [MessagesView](https://github.com/s4ysolutions/gem-demo/blob/main/app/src/main/java/solutions/s4y/jeleapps1/ui/composables/gcm/MessagesView.kt)
   - [SendView](https://github.com/s4ysolutions/gem-demo/blob/main/app/src/main/java/solutions/s4y/jeleapps1/ui/composables/gcm/SendView.kt) - commented out in the moment of writing this doc - development helper to bypass GC backend and send fake messages
  
- [PushNotificationPermitted](https://github.com/s4ysolutions/gem-demo/blob/main/app/src/main/java/solutions/s4y/jeleapps1/ui/composables/gcm/PushNotificationPermitted.kt) - a [wrapper](https://github.com/s4ysolutions/gem-demo/blob/e8df926c83e89c1580ad83efdfeadc5dfcf5c9a4/app/src/main/java/solutions/s4y/jeleapps1/MainActivity.kt#L28C1-L37C22) component
ensures the permission are implicitly requested using [Jetpack Compose Permissions](https://google.github.io/accompanist/permissions/) API
