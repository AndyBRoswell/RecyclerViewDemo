package top.abr.androidexp6

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.min

class SquareSprite {
    companion object {
        private val Count0 = 20
        private val Pattern = "\uD83E\uDD9F"
    }

    var CenterCoord: GCoord2D
    var MaxCoord: GCoord2D
    var EdgeLength: GCoordCpntT

    constructor(MaxCoord: GCoord2D, Coord: GCoord2D = Utils.RanGCoord2D(Origin, MaxCoord), EdgeLength: GCoordCpntT = min(MaxCoord.x, MaxCoord.y) / Count0) {
        this.CenterCoord = Coord
        this.EdgeLength = EdgeLength
        this.MaxCoord = MaxCoord
    }

    constructor(xmax: GCoordCpntT, ymax: GCoordCpntT, x: GCoordCpntT = Utils.RanFP32(Origin.x, xmax), y: GCoordCpntT = Utils.RanFP32(Origin.y, ymax), EdgeLength: GCoordCpntT = min(xmax, ymax) / Count0) {
        CenterCoord = Coord2D(x, y)
        this.EdgeLength = EdgeLength
        MaxCoord = Coord2D(xmax, ymax)
    }

    fun Shot(TouchCoord: GCoord2D): Boolean {
        val Diff = EdgeLength / 2
        return (TouchCoord.x >= CenterCoord.x - Diff) && (TouchCoord.x <= CenterCoord.x + Diff) && (TouchCoord.y >= CenterCoord.y - Diff) && (TouchCoord.y <= CenterCoord.y + Diff)
    }

    fun Shot(x: GCoordCpntT, y: GCoordCpntT): Boolean {
        val Diff = EdgeLength / 2
        return (x >= CenterCoord.x - Diff) && (x <= CenterCoord.x + Diff) && (y >= CenterCoord.y - Diff) && (y <= CenterCoord.y + Diff)
    }

    fun Move(DeltaC: GCoord2D, CorrectEnabled: Boolean = false): Boolean {
        CenterCoord.x += DeltaC.x
        CenterCoord.y += DeltaC.y
        return when (CorrectEnabled) {
            false -> OutOfBound()
            true -> CorrectOutOfBound()
        }
    }

    fun Move(DeltaX: GCoordCpntT, DeltaY: GCoordCpntT, CorrectEnabled: Boolean = false): Boolean {
        CenterCoord.x += DeltaX
        CenterCoord.y += DeltaY
        return when (CorrectEnabled) {
            false -> OutOfBound()
            true -> CorrectOutOfBound()
        }
    }

    private fun OutOfBound(): Boolean = (CenterCoord.x < 0) || (CenterCoord.y < 0) || (CenterCoord.x > MaxCoord.x) || (CenterCoord.y > MaxCoord.y)

    private fun CorrectOutOfBound(): Boolean {
        var Corrected = false
        if (CenterCoord.x < 0) {
            CenterCoord.x += MaxCoord.x
            Corrected = true
        }
        if (CenterCoord.y < 0) {
            CenterCoord.y += MaxCoord.y
            Corrected = true
        }
        if (CenterCoord.x > MaxCoord.x) {
            CenterCoord.x -= MaxCoord.x
            Corrected = true
        }
        if (CenterCoord.y > MaxCoord.y) {
            CenterCoord.y -= MaxCoord.y
            Corrected = true
        }
        return Corrected
    }

    fun DrawAt(Canvas: Canvas) {
        val Paint = Paint().apply { color = Color.BLACK }
        Canvas.drawText(Pattern, CenterCoord.x, CenterCoord.y, Paint)
    }
}