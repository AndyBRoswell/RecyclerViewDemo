package top.abr.androidexp6

import android.graphics.Bitmap
import android.graphics.Matrix
import java.util.*

class Utils {
    companion object {
        private val RandomSource = Random()

        fun RanFloat(Min: Float, Max: Float) = RandomSource.nextFloat()
        fun RanGraphicCoord2D(Min: Coord2D<GCoordCpntType>, Max: Coord2D<GCoordCpntType>) = Coord2D(RanFloat(Min.x, Max.x), RanFloat(Min.y, Max.y))
        fun RanGraphicCoord2D(xmin: GCoordCpntType, xmax: GCoordCpntType, ymin: GCoordCpntType, ymax: GCoordCpntType) = Coord2D(RanFloat(xmin, xmax), RanFloat(ymin, ymax))

        fun ZoomBitmap(B: Bitmap, NewWidth: Int = B.width, NewHeight: Int = B.height): Bitmap {
            val WidthMultiple = NewWidth.toFloat() / B.width
            val HeightMultiple = NewHeight.toFloat() / B.height
            val Matrix: Matrix = Matrix().apply {
                postScale(WidthMultiple, HeightMultiple)
            }
            return Bitmap.createBitmap(B, 0, 0, B.width, B.height, Matrix, true)
        }

        fun ZoomBitmap(B: Bitmap, WidthMultiple: Float, HeightMultiple: Float): Bitmap {
            val Matrix: Matrix = Matrix().apply {
                postScale(WidthMultiple, HeightMultiple)
            }
            return Bitmap.createBitmap(B, 0, 0, B.width, B.height, Matrix, true)
        }

        fun ZoomBitmap(B: Bitmap, Multiple: Float): Bitmap {
            val Matrix: Matrix = Matrix().apply {
                postScale(Multiple, Multiple)
            }
            return Bitmap.createBitmap(B, 0, 0, B.width, B.height, Matrix, true)
        }
    }
}