package dev.rostopira.rwgfy

import android.text.format.DateFormat
import android.util.Log
import com.squareup.moshi.adapter
import java.io.File
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.openapitools.client.infrastructure.Serializer
import rip.russianwarship.api.StatisticsApi
import rip.russianwarship.model.StatisticsObject

object Repo {
    private const val DATE_FORMAT = "yyyy-MM-dd"
    lateinit var cacheDir: File
    @OptIn(ExperimentalStdlibApi::class)
    private val moshi by lazy { Serializer.moshi.adapter<StatisticsObject>() }
    private val statsApi by lazy { StatisticsApi(client = HttpClient.okHttp) }

    suspend fun getTodaysData(): StatisticsObject? {
        val dateStr = DateFormat.format(DATE_FORMAT, Date())
        val fileName = "$dateStr.json"
        val file = File(cacheDir, fileName)
        if (file.exists()) {
            val json = file.readText()
            return moshi.fromJson(json)
        }
        val response = try {
            withContext(Dispatchers.IO) {
                statsApi.getStatisticByDate(LocalDate.now())
            }
        } catch (e: Exception) {
            Log.wtf("RWGFY", e)
            return null
        }
        val stats = response.data ?: return null
        val json = moshi.toJson(stats)
        file.writeText(json)
        return stats
    }

    @Suppress("NAME_SHADOWING")
    suspend fun getLastKnown(): StatisticsObject? {
        // Try to get yesterdays date from cache
        val yesterday = Date().toInstant().minus(1, ChronoUnit.DAYS).toEpochMilli()
        val dateStr = DateFormat.format(DATE_FORMAT, yesterday)
        val fileName = "$dateStr.json"
        val file = File(cacheDir, fileName)
        if (file.exists()) {
            val json = file.readText()
            return moshi.fromJson(json)
        }
        // Try to get latest available from API
        try {
            val stats = withContext(Dispatchers.IO) {
                statsApi.getLatestStatistics().data
            }
            if (stats != null) {
                val fileName = "${stats.date}.json"
                val file = File(cacheDir, fileName)
                if (!file.exists()) {
                    file.writeText(moshi.toJson(stats))
                }
                return stats
            }
        } catch (e: Exception) {
            Log.wtf("RWGFY", e)
        }
        return latestFromCache()
    }

    fun latestFromCache(): StatisticsObject? {
        val latest = cacheDir
            .listFiles()
            ?.sortedBy(File::lastModified)
            ?.firstOrNull()
            ?: return null
        return moshi.fromJson(latest.readText())
    }
}