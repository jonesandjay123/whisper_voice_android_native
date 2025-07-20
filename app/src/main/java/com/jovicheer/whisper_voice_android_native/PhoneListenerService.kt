package com.jovicheer.whisper_voice_android_native

import android.content.Intent
import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class PhoneListenerService : WearableListenerService() {

    override fun onCreate() {
        super.onCreate()
        Log.d("PhoneListener", "=== PhoneListenerService onCreate ===")
        Log.d("PhoneListener", "服務已創建並準備監聽來自手錶的訊息")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("PhoneListener", "=== PhoneListenerService onStartCommand ===")
        Log.d("PhoneListener", "Intent: $intent")
        Log.d("PhoneListener", "Flags: $flags, StartId: $startId")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        Log.d("PhoneListener", "=== 收到來自手錶的訊息 ===")
        Log.d("PhoneListener", "訊息路徑: ${messageEvent.path}")
        Log.d("PhoneListener", "來源節點ID: ${messageEvent.sourceNodeId}")
        
        if (messageEvent.path == "/get_input") {
            Log.d("PhoneListener", "處理取得輸入請求")
            val intent = Intent("com.jovicheer.ACTION_GET_INPUT")
            intent.putExtra("sourceNodeId", messageEvent.sourceNodeId)
            sendBroadcast(intent)
            Log.d("PhoneListener", "發送廣播給MainActivity")
            Log.d("PhoneListener", "廣播已發送")
        }
    }

    override fun onDestroy() {
        Log.d("PhoneListener", "=== PhoneListenerService onDestroy ===")
        super.onDestroy()
    }
}