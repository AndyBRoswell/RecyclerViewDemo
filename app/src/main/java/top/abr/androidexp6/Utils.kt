package top.abr.androidexp6

import android.graphics.Bitmap
import android.graphics.Matrix
import java.util.*

class Utils {
    companion object {
        private val RandomSource = Random()

        fun RanFP32(Min: Float, Max: Float) = (Max - Min) * RandomSource.nextFloat() + Min
        fun RanGCoord2D(Min: Coord2D<GCoordCpnt> = Origin, Max: Coord2D<GCoordCpnt>) = Coord2D(RanFP32(Min.x, Max.x), RanFP32(Min.y, Max.y))
        fun RanGCoord2D(xmin: GCoordCpnt = Origin.x, xmax: GCoordCpnt, ymin: GCoordCpnt = Origin.y, ymax: GCoordCpnt) = Coord2D(RanFP32(xmin, xmax), RanFP32(ymin, ymax))

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