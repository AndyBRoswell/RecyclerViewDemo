package top.abr.androidexp6

import android.graphics.Bitmap
import android.graphics.Matrix

class Utils {
    companion object {
        fun ZoomBitmap(B: Bitmap, NewWidth: Int = B.width, NewHeight: Int = B.height): Bitmap {
            val OldWidth = B.width
            val OldHeight = B.height
            val WidthMultiple = NewWidth.toFloat() / OldWidth
            val HeightMultiple = NewHeight.toFloat() / OldHeight
            val Matrix: Matrix = Matrix().apply {
                postScale(WidthMultiple, HeightMultiple)
            }
            return Bitmap.createBitmap(B, 0, 0, OldWidth, OldHeight, Matrix, true)
        }

        fun ZoomBitmap(B: Bitmap, WidthMultiple: Float, HeightMultiple: Float): Bitmap {
            val OldWidth = B.width
            val OldHeight = B.height
            val Matrix: Matrix = Matrix().apply {
                postScale(WidthMultiple, HeightMultiple)
            }
            return Bitmap.createBitmap(B, 0, 0, OldWidth, OldHeight, Matrix, true)
        }

        fun ZoomBitmap(B: Bitmap, Multiple: Float): Bitmap {
            val OldWidth = B.width
            val OldHeight = B.height
            val Matrix: Matrix = Matrix().apply {
                postScale(Multiple, Multiple)
            }
            return Bitmap.createBitmap(B, 0, 0, OldWidth, OldHeight, Matrix, true)
        }
    }
}