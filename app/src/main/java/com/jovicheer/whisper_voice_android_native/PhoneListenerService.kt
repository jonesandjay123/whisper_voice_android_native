package com.jovicheer.whisper_voice_android_native

import android.content.Intent
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class PhoneListenerService : WearableListenerService() {

    override fun onMessageReceived(messageEvent: MessageEvent) {
        // 只攔 "/get_input"
        if (messageEvent.path == "/get_input") {
            // 把手錶 nodeId 打包進廣播，發給 MainActivity
            Intent("com.jovicheer.ACTION_GET_INPUT").apply {
                putExtra("nodeId", messageEvent.sourceNodeId)
                sendBroadcast(this)
            }
        }
    }
}
