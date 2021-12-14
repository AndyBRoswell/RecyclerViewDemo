package top.abr.androidexp6

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

open class GameView : SurfaceView, SurfaceHolder.Callback {
    constructor(Context: Context) : super(Context)
    constructor(Context: Context, Attrs: AttributeSet) : super(Context, Attrs)
    constructor(Context: Context, Attrs: AttributeSet, DefStyleAttr: Int) : super(Context, Attrs, DefStyleAttr)
    constructor(Context: Context, Attrs: AttributeSet, DefStyleAttr: Int, DefStyleRes: Int) : super(Context, Attrs, DefStyleAttr, DefStyleRes)

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(Holder: SurfaceHolder) {

    }

    override fun surfaceChanged(Holder: SurfaceHolder, Format: Int, Width: Int, Height: Int) {}

    override fun surfaceDestroyed(Holder: SurfaceHolder) {
        
    }
}