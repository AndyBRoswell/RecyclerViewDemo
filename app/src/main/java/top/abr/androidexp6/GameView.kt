package top.abr.androidexp6

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.min

open class GameView : SurfaceView, SurfaceHolder.Callback {
    private inner class DrawingThread : Thread() {
        private val MaxCoord = Coord2D(this@GameView.width.toFloat(), this@GameView.height.toFloat())
        private val EdgeLen = min(MaxCoord.x, MaxCoord.y) / 16

        private val SpriteCount = 2
        private val Sprites = ArrayList<SquareSprite>()
        private var CanRun = true

        init {
            for (i in 1..SpriteCount) Sprites.add(SquareSprite(MaxCoord, EdgeLength = EdgeLen))
        }

        fun Stop() {
            CanRun = false
        }

        override fun run() {
            super.run()

            while (CanRun) {
                val Canvas = holder.lockCanvas()
                Canvas.drawColor(Color.GRAY)
                if (this@GameView.Touched) {
                    for (Sprite in Sprites) {
                        if (Sprite.Shot(TouchCoord)) {
                            ++Hit
                            Sprite.CenterCoord = Utils.RanGCoord2D(Max = MaxCoord)
                        }
                    }
                }
                for (Sprite in Sprites) {
                    val k = 0.01F
                    val DeltaGCoord = Utils.RanGCoord2D(GCoord2D(-k * EdgeLen, -k * EdgeLen), GCoord2D(k * EdgeLen, k * EdgeLen))
                    when (Sprite.Move(DeltaGCoord)) {
                        false -> {}
                        true -> {
                            ++Missed
                            Sprite.CenterCoord = Utils.RanGCoord2D(Max = MaxCoord)
                        }
                    }
                    Sprite.DrawAt(Canvas)
                }
                this@GameView.Touched = false
                val Paint = Paint().apply {
                    textSize = 100F
                    color = Color.GREEN
                }
                Canvas.drawText("Hit: $Hit    Missed: $Missed", 100F, 100F, Paint)
                holder.unlockCanvasAndPost(Canvas)
            }
        }
    }

    private lateinit var MainDrawingThread: DrawingThread
    private var TouchCoord = Coord2D(-1.0f, -1.0f)
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
        setOnTouchListener { V, Motion ->
            if (Motion.action == MotionEvent.ACTION_DOWN) {
                TouchCoord = Coord2D(Motion.x, Motion.y)
                Touched = true
            }
            false
        }
        MainDrawingThread = DrawingThread().apply { start() }
    }

    override fun surfaceChanged(Holder: SurfaceHolder, Format: Int, Width: Int, Height: Int) {}

    override fun surfaceDestroyed(Holder: SurfaceHolder) {
        MainDrawingThread.Stop()
        MainDrawingThread.join()
    }
}