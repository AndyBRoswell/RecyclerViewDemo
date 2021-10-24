package top.abr.androidexp6

import android.content.Context
import android.util.AttributeSet
import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class RecyclerViewWithContextMenu : RecyclerView {
	open class RecyclerViewContextInfo : ContextMenu.ContextMenuInfo {
		var Position = -1
	}

	var ContextInfo: RecyclerViewContextInfo = RecyclerViewContextInfo()
		private set

	constructor(Ctx: Context) : super(Ctx)

	constructor(Ctx: Context, Attrs: AttributeSet?) : super(Ctx, Attrs)

	constructor(Ctx: Context, Attrs: AttributeSet?, DefStyleAttr: Int) : super(Ctx, Attrs, DefStyleAttr)

	private fun GetChildPosition(OriginalView: View) {
		if (layoutManager != null) {
			val Pos = layoutManager!!.getPosition(OriginalView)
			ContextInfo.Position = Pos
		}
	}

	override fun showContextMenuForChild(OriginalView: View?): Boolean {
		GetChildPosition(OriginalView!!)
		return super.showContextMenuForChild(OriginalView)
	}

	override fun showContextMenuForChild(OriginalView: View?, x: Float, y: Float): Boolean {
		GetChildPosition(OriginalView!!)
		return super.showContextMenuForChild(OriginalView, x, y)
	}
}