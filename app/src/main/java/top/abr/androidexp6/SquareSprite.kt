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
    var EdgeLength: GCoordCpnt

    constructor(MaxCoord: GCoord2D, Coord: GCoord2D = Utils.RanGCoord2D(Origin, MaxCoord), EdgeLength: GCoordCpnt = min(MaxCoord.x, MaxCoord.y) / Count0) {
        this.CenterCoord = Coord
        this.EdgeLength = EdgeLength
        this.MaxCoord = MaxCoord
    }

    constructor(xmax: GCoordCpnt, ymax: GCoordCpnt, x: GCoordCpnt = Utils.RanFP32(Origin.x, xmax), y: GCoordCpnt = Utils.RanFP32(Origin.y, ymax), EdgeLength: GCoordCpnt = min(xmax, ymax) / Count0) {
        CenterCoord = Coord2D(x, y)
        this.EdgeLength = EdgeLength
        MaxCoord = Coord2D(xmax, ymax)
    }

    fun Shot(TouchCoord: GCoord2D): Boolean {
        val Err = EdgeLength
        return (TouchCoord.x >= CenterCoord.x - Err) && (TouchCoord.x <= CenterCoord.x + Err) && (TouchCoord.y >= CenterCoord.y - Err) && (TouchCoord.y <= CenterCoord.y + Err)
    }

    fun Shot(x: GCoordCpnt, y: GCoordCpnt): Boolean {
        val Err = EdgeLength
        return (x >= CenterCoord.x - Err) && (x <= CenterCoord.x + Err) && (y >= CenterCoord.y - Err) && (y <= CenterCoord.y + Err)
    }

    fun Move(DeltaC: GCoord2D, CorrectEnabled: Boolean = false): Boolean {
        CenterCoord.x += DeltaC.x
        CenterCoord.y += DeltaC.y
        return when (CorrectEnabled) {
            false -> OutOfBound()
            true -> CorrectOutOfBound()
        }
    }

    fun Move(DeltaX: GCoordCpnt, DeltaY: GCoordCpnt, CorrectEnabled: Boolean = false): Boolean {
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
        val Paint = Paint().apply {
            textSize = EdgeLength
            color = Color.BLACK
        }
        Canvas.drawText(Pattern, CenterCoord.x, CenterCoord.y, Paint)
    }
}