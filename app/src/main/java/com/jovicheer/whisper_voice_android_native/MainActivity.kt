package com.jovicheer.whisper_voice_android_native

import android.Manifest                      // ← 權限常量
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.content.Context.RECEIVER_NOT_EXPORTED
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts // ← 你用到的 API
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.wearable.Wearable
import com.jovicheer.whisper_voice_android_native.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val requestNoti = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* 不用特別處理 */ }

    private val inputRequestReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val nodeId = intent?.getStringExtra("nodeId") ?: return
            val msg    = binding.inputField.text.toString()

            Wearable.getMessageClient(this@MainActivity)
                .sendMessage(nodeId, "/input_response", msg.toByteArray())
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Android 13+ 要求通知權限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                requestNoti.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        binding.inputField.setOnEditorActionListener { v, _, _ ->
            android.util.Log.d("MainActivity", "Typed: ${v.text}")
            false
        }

        val filter = IntentFilter("com.jovicheer.ACTION_GET_INPUT")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(inputRequestReceiver, filter, RECEIVER_NOT_EXPORTED)
        } else {
            @Suppress("DEPRECATION")
            registerReceiver(inputRequestReceiver, filter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(inputRequestReceiver)
    }

}