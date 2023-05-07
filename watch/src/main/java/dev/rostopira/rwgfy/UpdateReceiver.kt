package dev.rostopira.rwgfy

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.wear.tiles.TileService

class UpdateReceiver: BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        if (BuildConfig.DEBUG) {
            Log.wtf("UpdateReceiver", "Package updated")
        }
        TileService.getUpdater(context).requestUpdate(RwgfyTileService::class.java)
    }
}