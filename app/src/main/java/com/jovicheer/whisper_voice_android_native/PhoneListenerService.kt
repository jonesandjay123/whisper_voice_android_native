package com.jovicheer.whisper_voice_android_native

import android.content.Intent
import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class PhoneListenerService : WearableListenerService() {

    override fun onMessageReceived(messageEvent: MessageEvent) {
        Log.d("PhoneListener", "－－－－－onMessageReceived")
        // 只攔 "/get_input"
        if (messageEvent.path == "/get_input") {
            Log.d("PhoneListener", "－－－－－－Received /get_input")
            // 把手錶 nodeId 打包進廣播，發給 MainActivity
            Intent("com.jovicheer.ACTION_GET_INPUT").apply {
                putExtra("nodeId", messageEvent.sourceNodeId)
                sendBroadcast(this)
            }
        }
    }
}
