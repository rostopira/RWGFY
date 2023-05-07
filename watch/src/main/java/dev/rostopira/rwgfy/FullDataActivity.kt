package dev.rostopira.rwgfy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext
import rip.russianwarship.model.StatisticsObject

class FullDataActivity: ComponentActivity(), CoroutineScope {
    lateinit var stats: StatisticsObject

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launch {
            val s = Repo.getTodaysData() ?: Repo.getLastKnown()
            if (s == null) {
                finish()
                return@launch
            }
            stats = s
            setContent {
                GenshtabTheme {
                    val focusRequester = remember { FocusRequester() }
                    val listState = rememberLazyListState()
                    val coroutineScope = rememberCoroutineScope()
                    LazyColumn(
                        modifier = Modifier.Companion
                            .onRotaryScrollEvent {
                                coroutineScope.launch { listState.scrollBy(it.verticalScrollPixels) }
                                true
                            }
                            .focusRequester(focusRequester)
                            .fillMaxWidth()
                            .focusable(),
                        state = listState,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(vertical = 8f.dp),

                    ) {
                        items(StatsField.values().count()) {
                            ValueBlock(StatsField.values()[it])
                        }
                    }
                    LaunchedEffect(Unit) { focusRequester.requestFocus() }
                }
            }
        }
    }

    @Composable
    private fun ValueBlock(field: StatsField) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8f.dp)
                .fillMaxWidth(),
        ) {
            BasicText(
                text = getString(field.shortDescriptionId),
                style = CustomTheme.typography.title,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BasicText(
                    text = field.getterTotal(stats).toString(),
                    style = CustomTheme.typography.total,
                )
                val inc = field.getterIncrease(stats)
                if (inc != null)
                    BasicText(
                        text = "+$inc",
                        style = CustomTheme.typography.increase
                    )
            }
        }
    }

    override val coroutineContext: CoroutineContext get() = Dispatchers.Main
}