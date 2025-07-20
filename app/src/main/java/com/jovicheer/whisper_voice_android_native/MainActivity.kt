package com.jovicheer.whisper_voice_android_native

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jovicheer.whisper_voice_android_native.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 目前只有印 log，確認 EditText 可正常存取
        binding.inputField.setOnEditorActionListener { v, _, _ ->
            android.util.Log.d("MainActivity", "Typed: ${v.text}")
            false
        }
    }
}