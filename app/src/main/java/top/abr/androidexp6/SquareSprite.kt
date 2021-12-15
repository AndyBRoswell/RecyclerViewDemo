package top.abr.androidexp6

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class SquareSprite {
    private val Pattern = "\uD83E\uDD9F"

    var CenterCoord: Coord2D<GCoordCpntType>
    var MaxCoord: Coord2D<GCoordCpntType>
    var EdgeLength: GCoordCpntType

    constructor(MaxCoord: Coord2D<GCoordCpntType>, Coord: Coord2D<GCoordCpntType> = Utils.RanGraphicCoord2D(Origin, MaxCoord), EdgeLength: GCoordCpntType) {
        this.CenterCoord = Coord
        this.EdgeLength = EdgeLength
        this.MaxCoord = MaxCoord
    }

    constructor(xmax: GCoordCpntType, ymax: GCoordCpntType, x: GCoordCpntType = Utils.RanFloat(Origin.x, xmax), y: GCoordCpntType = Utils.RanFloat(Origin.y, ymax), EdgeLength: GCoordCpntType) {
        CenterCoord = Coord2D(x, y)
        this.EdgeLength = EdgeLength
        MaxCoord = Coord2D(xmax, ymax)
    }

    fun Shot(TouchCoord: Coord2D<GCoordCpntType>): Boolean {
        val Diff = EdgeLength / 2
        return (TouchCoord.x >= CenterCoord.x - Diff) && (TouchCoord.x <= CenterCoord.x + Diff) && (TouchCoord.y >= CenterCoord.y - Diff) && (TouchCoord.y <= CenterCoord.y + Diff)
    }

    fun Shot(x: GCoordCpntType, y: GCoordCpntType): Boolean {
        val Diff = EdgeLength / 2
        return (x >= CenterCoord.x - Diff) && (x <= CenterCoord.x + Diff) && (y >= CenterCoord.y - Diff) && (y <= CenterCoord.y + Diff)
    }

    fun Move(DeltaC: Coord2D<GCoordCpntType>, CorrectEnabled: Boolean = false): Boolean {
        CenterCoord.x += DeltaC.x
        CenterCoord.y += DeltaC.y
        return when (CorrectEnabled) {
            false -> OutOfBound()
            true -> CorrectOutOfBound()
        }
    }

    fun Move(DeltaX: GCoordCpntType, DeltaY: GCoordCpntType, CorrectEnabled: Boolean = false): Boolean {
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