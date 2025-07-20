package com.jovicheer.whisper_voice_android_native

import android.content.Intent
import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class PhoneListenerService : WearableListenerService() {

    companion object {
        private const val TAG = "PhoneListener"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "=== PhoneListenerService onCreate ===")
        Log.d(TAG, "服務已創建並準備監聽來自手錶的訊息")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "=== PhoneListenerService onStartCommand ===")
        Log.d(TAG, "Intent: ${intent?.action}")
        Log.d(TAG, "Flags: $flags, StartId: $startId")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        Log.d(TAG, "=== 收到來自手錶的訊息 ===")
        Log.d(TAG, "訊息路徑: ${messageEvent.path}")
        Log.d(TAG, "來源節點: ${messageEvent.sourceNodeId}")
        Log.d(TAG, "資料長度: ${messageEvent.data?.size ?: 0} bytes")
        Log.d(TAG, "時間戳: ${System.currentTimeMillis()}")
        
        // 只攔 "/get_input"
        if (messageEvent.path == "/get_input") {
            Log.d(TAG, "－－－－－－Received /get_input")
            // 把手錶 nodeId 打包進廣播，發給 MainActivity
            Intent("com.jovicheer.ACTION_GET_INPUT").apply {
                putExtra("nodeId", messageEvent.sourceNodeId)
                Log.d(TAG, "發送廣播給MainActivity，節點ID: ${messageEvent.sourceNodeId}")
                sendBroadcast(this)
                Log.d(TAG, "廣播已發送")
            }
        } else {
            Log.w(TAG, "未知的訊息路徑: ${messageEvent.path}")
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "=== PhoneListenerService onDestroy ===")
        super.onDestroy()
    }
}
