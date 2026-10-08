package com.fl8y.litecloner.shizuku

import android.app.Activity
import android.content.ComponentName
import android.os.IBinder
import android.widget.Toast
import rikka.shizuku.Shizuku
import rikka.shizuku.Shizuku.UserServiceArgs

class ShizukuController(
    private val activity: Activity,
    private val onStateChanged: () -> Unit
) {
    companion object { private const val REQUEST_CODE = 1001 }

    private val permissionListener = Shizuku.OnRequestPermissionResultListener { code, result ->
        if (code == REQUEST_CODE) {
            Toast.makeText(
                activity,
                if (result == android.content.pm.PackageManager.PERMISSION_GRANTED)
                    "Shizuku permission granted" else "Shizuku permission denied",
                Toast.LENGTH_SHORT
            ).show()
            onStateChanged()
        }
    }

    init {
        Shizuku.addRequestPermissionResultListener(permissionListener)
        onStateChanged()
    }

    fun isReady() = Shizuku.pingBinder() &&
        Shizuku.checkSelfPermission() == android.content.pm.PackageManager.PERMISSION_GRANTED

    fun ensurePermission() {
        if (!Shizuku.pingBinder()) {
            Toast.makeText(activity, "Start Shizuku first", Toast.LENGTH_LONG).show()
            return
        }
        if (Shizuku.checkSelfPermission() != android.content.pm.PackageManager.PERMISSION_GRANTED)
            Shizuku.requestPermission(REQUEST_CODE)
        else Toast.makeText(activity, "Shizuku is already authorized", Toast.LENGTH_SHORT).show()
        onStateChanged()
    }

    fun startUserService() {
        if (!isReady()) return
        val args = UserServiceArgs(ComponentName(activity, LiteClonerUserService::class.java))
            .daemon(false).debuggable(true).processNameSuffix("litecloner").version(1).tag("lite-cloner")

        Shizuku.bindUserService(args, object : Shizuku.ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                Toast.makeText(activity, "Lite Cloner Shizuku service started", Toast.LENGTH_SHORT).show()
                onStateChanged()
            }
            override fun onServiceDisconnected(name: ComponentName) { onStateChanged() }
        })
    }

    fun destroy() { Shizuku.removeRequestPermissionResultListener(permissionListener) }
}
