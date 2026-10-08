package com.fl8y.litecloner

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fl8y.litecloner.databinding.ActivityMainBinding
import com.fl8y.litecloner.shizuku.ShizukuController
import rikka.shizuku.Shizuku

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var shizukuController: ShizukuController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shizukuController = ShizukuController(this) { updateStatus() }
        binding.shizukuButton.setOnClickListener { shizukuController.ensurePermission() }
        binding.startButton.setOnClickListener {
            if (shizukuController.isReady()) shizukuController.startUserService()
            else Toast.makeText(this, "Connect Shizuku first", Toast.LENGTH_SHORT).show()
        }
        updateStatus()
    }

    private fun updateStatus() {
        binding.statusText.text = when {
            !Shizuku.pingBinder() -> "Shizuku is not running"
            Shizuku.checkSelfPermission() == android.content.pm.PackageManager.PERMISSION_GRANTED ->
                "Shizuku connected and authorized"
            else -> "Shizuku connected — permission required"
        }
    }

    override fun onDestroy() {
        shizukuController.destroy()
        super.onDestroy()
    }
}
