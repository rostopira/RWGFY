package dev.rostopira.rwgfy

import android.util.Log
import androidx.wear.watchface.complications.data.*
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceService
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rip.russianwarship.model.StatisticsObject
import kotlin.coroutines.CoroutineContext

class RwgfyComplicationProvider: ComplicationDataSourceService(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    override fun getPreviewData(type: ComplicationType): ComplicationData {
        val stats = Repo.latestFromCache() ?: return EmptyComplicationData()
        val personnelUnits = stats.asComplicationData(type)
        val text = PlainComplicationText.Builder(personnelUnits.toString()).build()
        return ShortTextComplicationData.Builder(text, text).build()
    }

    override fun onComplicationRequest(
        request: ComplicationRequest,
        listener: ComplicationRequestListener
    ) {
        launch {
            val stats = try {
                Repo.getTodaysData() ?: Repo.getLastKnown()
            } catch (e: Exception) {
                Log.wtf(TAG, e)
                return@launch
            } ?: return@launch
            listener.onComplicationData(stats.asComplicationData(request.complicationType))
        }
    }

    companion object {
        const val TAG = "RwgfyComplicationProvider"
        fun StatisticsObject.asComplicationData(type: ComplicationType) =
            when(type) {
                ComplicationType.SHORT_TEXT -> {
                    val str = "+${(increase?.personnelUnits ?: 0)}"
                    val text = PlainComplicationText.Builder(str).build()
                    ShortTextComplicationData.Builder(text, text).build()
                }
                ComplicationType.LONG_TEXT -> {
                    val str = "${stats.personnelUnits} (+${(increase?.personnelUnits ?: 0)})"
                    val text = PlainComplicationText.Builder(str).build()
                    LongTextComplicationData.Builder(text, text).build()
                }
                else -> EmptyComplicationData()
            }
    }
}