package top.abr.androidexp6

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

open class GameView : SurfaceView, SurfaceHolder.Callback {
    private inner class DrawingThread() : Thread() {
        private val SpriteCount = 2
        private val Sprites = ArrayList<Sprite>()
        private var CanRun = true

        init {
            for (i in 1..SpriteCount) Sprites.add(Sprite())
        }

        fun Stop() {
            CanRun = false
        }

        override fun run() {
            super.run()

            while (CanRun) {
                val Canvas = holder.lockCanvas()
                Canvas.drawColor(Color.GRAY)

            }
        }
    }

    private inner class Coordinate(var x: Float = 0.0f, var y: Float = 0.0f)

    private lateinit var MainDrawingThread: DrawingThread
    private var TouchCoord = Coordinate(-1.0f, -1.0f)
    private var Touched = false
    private var Hit = 0L
    private var Missed = 0L

    constructor(Context: Context) : super(Context)
    constructor(Context: Context, Attrs: AttributeSet) : super(Context, Attrs)
    constructor(Context: Context, Attrs: AttributeSet, DefStyleAttr: Int) : super(Context, Attrs, DefStyleAttr)
    constructor(Context: Context, Attrs: AttributeSet, DefStyleAttr: Int, DefStyleRes: Int) : super(Context, Attrs, DefStyleAttr, DefStyleRes)

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(Holder: SurfaceHolder) {
        this@GameView.setOnTouchListener { V, Motion ->
            if (Motion.action == MotionEvent.ACTION_DOWN) {
                TouchCoord = Coordinate(Motion.x, Motion.y)
                Touched = true
            }
            false
        }
        MainDrawingThread = DrawingThread().apply { start() }
    }

    override fun surfaceChanged(Holder: SurfaceHolder, Format: Int, Width: Int, Height: Int) {}

    override fun surfaceDestroyed(Holder: SurfaceHolder) {

    }
}