package top.abr.androidexp6

import android.graphics.Canvas

typealias GraphicCoordCpntType = Float

class SquareSprite {
    var Coord: Coord2D<GraphicCoordCpntType>
    var EdgeLength: GraphicCoordCpntType

    constructor(Coord: Coord2D<GraphicCoordCpntType>, EdgeLength: GraphicCoordCpntType) {
        this.Coord = Coord
        this.EdgeLength = EdgeLength
    }

    constructor(x: GraphicCoordCpntType, y: GraphicCoordCpntType, EdgeLength: GraphicCoordCpntType) {
        Coord = Coord2D(x, y)
        this.EdgeLength = EdgeLength
    }

    fun Shot(TouchCoord: Coord2D<GraphicCoordCpntType>): Boolean {

    }

    fun Shot(x: GraphicCoordCpntType, y: GraphicCoordCpntType): Boolean {

    }

    fun Move(DeltaC: Coord2D<GraphicCoordCpntType>) {

    }

    fun DrawAt(Canvas: Canvas) {

    }
}