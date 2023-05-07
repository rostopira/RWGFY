package dev.rostopira.rwgfy

import androidx.wear.tiles.ColorBuilders.ColorProp
import androidx.wear.tiles.DimensionBuilders
import androidx.wear.tiles.LayoutElementBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.TimelineBuilders

inline fun tile(builderFunc: TileBuilders.Tile.Builder.() -> Unit): TileBuilders.Tile {
    return TileBuilders.Tile.Builder().apply(builderFunc).build()
}

inline fun TileBuilders.Tile.Builder.timeline(builderFunc: TimelineBuilders.Timeline.Builder.() -> Unit) {
    setTimeline(TimelineBuilders.Timeline.Builder().apply(builderFunc).build())
}

inline fun TimelineBuilders.Timeline.Builder.timelineEntry(builderFunc: TimelineBuilders.TimelineEntry.Builder.() -> Unit) {
    addTimelineEntry(TimelineBuilders.TimelineEntry.Builder().apply(builderFunc).build())
}

fun layout(layout: LayoutElementBuilders.LayoutElement) =
    LayoutElementBuilders.Layout.Builder().setRoot(layout).build()

inline fun column(content: LayoutElementBuilders.Column.Builder.() -> Unit) =
    LayoutElementBuilders.Column.Builder().apply(content).build()

inline fun row(content: LayoutElementBuilders.Row.Builder.() -> Unit) =
    LayoutElementBuilders.Row.Builder().apply(content).build()

inline fun box(content: LayoutElementBuilders.Box.Builder.() -> Unit) =
    LayoutElementBuilders.Box.Builder().apply(content).build()

fun LayoutElementBuilders.Row.Builder.children(elements: List<LayoutElementBuilders.LayoutElement>) {
    for (element in elements) {
        addContent(element)
    }
}

fun LayoutElementBuilders.Column.Builder.children(elements: List<LayoutElementBuilders.LayoutElement>) {
    for (element in elements) {
        addContent(element)
    }
}

fun LayoutElementBuilders.Column.Builder.children(vararg elements: LayoutElementBuilders.LayoutElement) {
    for (element in elements) {
        addContent(element)
    }
}

fun LayoutElementBuilders.Box.Builder.children(vararg elements: LayoutElementBuilders.LayoutElement) {
    for (element in elements) {
        addContent(element)
    }
}

inline fun text(layout: LayoutElementBuilders.Text.Builder.() -> Unit) =
    LayoutElementBuilders.Text.Builder().apply(layout).build()

inline fun image(layout: LayoutElementBuilders.Image.Builder.() -> Unit) =
    LayoutElementBuilders.Image.Builder().apply(layout).build()

inline fun LayoutElementBuilders.Text.Builder.fontStyle(block: LayoutElementBuilders.FontStyle.Builder.() -> Unit) {
    setFontStyle(LayoutElementBuilders.FontStyle.Builder().apply(block).build())
}

fun LayoutElementBuilders.FontStyle.Builder.setColor(color: Int) {
    setColor(ColorProp.Builder().setArgb(color).build())
}

@Suppress("unused") // Receiver is unused but required for context
fun LayoutElementBuilders.Row.Builder.spacer(size: DimensionBuilders.SpacerDimension) =
    LayoutElementBuilders.Spacer.Builder().setWidth(size).setHeight(0.dp).build()

@Suppress("unused") // Receiver is unused but required for context
fun LayoutElementBuilders.Column.Builder.spacer(size: DimensionBuilders.SpacerDimension) =
    LayoutElementBuilders.Spacer.Builder().setWidth(0.dp).setHeight(size).build()

val Float.sp: DimensionBuilders.SpProp get() = DimensionBuilders.sp(this)
val Int.sp: DimensionBuilders.SpProp get() = DimensionBuilders.sp(toFloat())

val Float.dp: DimensionBuilders.DpProp get() = DimensionBuilders.dp(this)
val Int.dp: DimensionBuilders.DpProp get() = DimensionBuilders.dp(toFloat())