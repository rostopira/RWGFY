package dev.rostopira.rwgfy

import android.app.Application
import dev.rostopira.rwgfy.BuildConfig.*

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Repo.cacheDir = cacheDir
        HttpClient.userAgent = "Android ${APPLICATION_ID}/${VERSION_NAME} (${VERSION_CODE})"
    }

}