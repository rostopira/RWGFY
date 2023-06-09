package dev.rostopira.rwgfy

import android.graphics.Color
import android.text.format.DateUtils
import android.util.Log
import androidx.wear.tiles.*
import dev.rostopira.rwgfy.shared.R as S
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.guava.future
import kotlin.coroutines.CoroutineContext

class RwgfyTileService: TileService(), CoroutineScope {

    override fun onTileRequest(requestParams: RequestBuilders.TileRequest) = future {
        val stats = try {
            Repo.getTodaysData() ?: Repo.getLastKnown()
        } catch (e: Exception) {
            Log.wtf(TAG, e)
            return@future errorTile(e.message ?: "null")
        }
        stats ?: return@future errorTile("failed to get data")
        if (BuildConfig.DEBUG) {
            Log.wtf(TAG, "Building new tile ${stats.stats.personnelUnits}")
        }
        return@future tile {
            setResourcesVersion(RESOURCES_VERSION)
            timeline {
                val nextUpdate = UkraineClock.nextUpdateTime(stats.date)
                val now = System.currentTimeMillis()
                setFreshnessIntervalMillis(
                    if (nextUpdate < now)
                        DateUtils.MINUTE_IN_MILLIS * 15
                    else
                        nextUpdate - now
                )
                timelineEntry {
                    setLayout(layout (
                        box {
                            children(
                                bg(),
                                column {
                                    children(
                                        text {
                                            setText(getString(S.string.tile_header))
                                            fontStyle {
                                                setColor(getColor(S.color.genshtab_yellow))
                                                setSize(12.sp)
                                                setMaxLines(2)
                                            }
                                        },
                                        spacer(5.dp),
                                        row {
                                            children(
                                                stats.stats.personnelUnits.toString().asImages(34)
                                            )
                                        },
                                        spacer(5.dp),
                                        row {
                                            children(
                                                "+${stats.increase?.personnelUnits}".asImages(20)
                                            )
                                        },
                                        spacer(5.dp),
                                        text {
                                            setText(
                                                getString(S.string.date_prefix, stats.date.format(dateFormat))
                                            )
                                            fontStyle {
                                                setColor(getColor(S.color.genshtab_yellow))
                                                setSize(12.sp)
                                                setMaxLines(2)
                                            }
                                        },
                                        spacer(5.dp),
                                        row {
                                            children(
                                                refresh(),
                                                spacer(5.dp),
                                                showFull(),
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    ))
                }
            }
        }
    }

    private fun errorTile(error: String) = tile {
        setResourcesVersion(RESOURCES_VERSION)
        timeline {
            timelineEntry {
                if (BuildConfig.DEBUG) {
                    setFreshnessIntervalMillis(DateUtils.MINUTE_IN_MILLIS)
                } else {
                    setFreshnessIntervalMillis(DateUtils.MINUTE_IN_MILLIS * 15)
                }
                setLayout(layout(
                    box {
                        children(
                            bg(),
                            text {
                                setText(error)
                                fontStyle {
                                    setColor(Color.RED)
                                    setSize(18.sp)
                                }
                            }
                        )
                    }
                ))
            }
        }
    }

    private fun bg() = image {
        setResourceId("genshtab")
        val ss = screenSize()
        setWidth(ss.dp)
        setHeight(ss.dp)
    }

    private fun refresh() = image {
        setResourceId("refresh")
        setWidth(24.dp)
        setHeight(24.dp)
        setModifiers(modifiers {
            setClickable(refreshClick("refresh"))
        })
    }

    private fun showFull() = image {
        setResourceId("expand")
        setWidth(24.dp)
        setHeight(24.dp)
        setModifiers(modifiers {
            setClickable(
                ModifiersBuilders.Clickable.Builder()
                    .setId("expand")
                    .setOnClick(
                        ActionBuilders.LaunchAction.Builder()
                        .setAndroidActivity(
                            ActionBuilders.AndroidActivity.Builder()
                                .setClassName(FullDataActivity::class.java.name)
                                .setPackageName(BuildConfig.APPLICATION_ID)
                            .build()
                        ).build()
                    )
                .build()
            )
        })
    }

    override val coroutineContext: CoroutineContext get() = Dispatchers.Main

    override fun onResourcesRequest(requestParams: RequestBuilders.ResourcesRequest) = future {
        ResourceBuilders.Resources.Builder().apply {
            if (BuildConfig.DEBUG) {
                Log.wtf(TAG, "Requested resources: " + requestParams.resourceIds.joinToString())
            }
            addDrawable("genshtab", S.drawable.genshtab_bg)
            addDrawable("refresh", S.drawable.baseline_refresh_24)
            addDrawable("expand", S.drawable.baseline_expand_more_24)
            DrawableFont.values().forEach {
                addDrawable(it.id, it.resId)
            }
            setVersion(RESOURCES_VERSION)
        }.build()
    }

    companion object {
        private const val TAG = "RwgfyTileService"
        const val RESOURCES_VERSION = "9"
        private fun String.asImages(size: Int) =
            toCharArray()
                .mapNotNull(DrawableFont::byChar)
                .map { it.asImage(size) }
        private val dateFormat: DateTimeFormatter get() = DateTimeFormatter.ISO_DATE
    }
}