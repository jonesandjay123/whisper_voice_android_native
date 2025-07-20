package com.jovicheer.whisper_voice_android_native

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.content.Context.RECEIVER_NOT_EXPORTED
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.wearable.Wearable
import com.jovicheer.whisper_voice_android_native.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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