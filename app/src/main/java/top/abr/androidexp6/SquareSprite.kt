package top.abr.androidexp6

import android.graphics.Canvas

typealias GraphicCoordCpntType = Float

class SquareSprite {
    var CenterCoord: Coord2D<GraphicCoordCpntType>
    var MaxCoord: Coord2D<GraphicCoordCpntType>
    var EdgeLength: GraphicCoordCpntType

    constructor(Coord: Coord2D<GraphicCoordCpntType>, EdgeLength: GraphicCoordCpntType, MaxCoord: Coord2D<GraphicCoordCpntType>) {
        this.CenterCoord = Coord
        this.EdgeLength = EdgeLength
        this.MaxCoord = MaxCoord
    }

    constructor(x: GraphicCoordCpntType, y: GraphicCoordCpntType, EdgeLength: GraphicCoordCpntType, xmax: GraphicCoordCpntType, ymax: GraphicCoordCpntType) {
        CenterCoord = Coord2D(x, y)
        this.EdgeLength = EdgeLength
        MaxCoord = Coord2D(xmax, ymax)
    }

    fun Shot(TouchCoord: Coord2D<GraphicCoordCpntType>): Boolean {
        val Diff = EdgeLength / 2
        return (TouchCoord.x >= CenterCoord.x - Diff) && (TouchCoord.x <= CenterCoord.x + Diff) && (TouchCoord.y >= CenterCoord.y - Diff) && (TouchCoord.y <= CenterCoord.y + Diff)
    }

    fun Shot(x: GraphicCoordCpntType, y: GraphicCoordCpntType): Boolean {
        val Diff = EdgeLength / 2
        return (x >= CenterCoord.x - Diff) && (x <= CenterCoord.x + Diff) && (y >= CenterCoord.y - Diff) && (y <= CenterCoord.y + Diff)
    }

    fun Move(DeltaC: Coord2D<GraphicCoordCpntType>) {
        CenterCoord.x += DeltaC.x
        CenterCoord.y += DeltaC.y
        CorrectOutOfBound()
    }

    fun Move(DeltaX: GraphicCoordCpntType, DeltaY: GraphicCoordCpntType) {
        CenterCoord.x += DeltaX
        CenterCoord.y += DeltaY
        CorrectOutOfBound()
    }

    private fun CorrectOutOfBound() {
        if (CenterCoord.x < 0) CenterCoord.x += MaxCoord.x
        if (CenterCoord.y < 0) CenterCoord.y += MaxCoord.y
        if (CenterCoord.x > MaxCoord.x) CenterCoord.x -= MaxCoord.x
        if (CenterCoord.y > MaxCoord.y) CenterCoord.y -= MaxCoord.y
    }

    fun DrawAt(Canvas: Canvas) {

    }
}