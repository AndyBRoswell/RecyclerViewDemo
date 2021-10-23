package top.abr.androidexp6

import android.content.Context
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

open class BooksAdapter(var BookList: ArrayList<Book>) : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {
	inner class BookViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
		val BookCoverView: ImageView = ItemView.findViewById(R.id.image_view_book_cover)
		val BookTitleView: TextView = ItemView.findViewById(R.id.text_view_book_title)

		init {
			ItemView.setOnCreateContextMenuListener(this)
		}

		fun Bind(B: Book) {
			BookCoverView.setImageResource(B.CoverResourceID)
			BookTitleView.text = B.Title
		}

//		override fun onCreateContextMenu(M: ContextMenu?, V: View?, MenuInfo: ContextMenu.ContextMenuInfo?) {
//			val GroupID = 0
//			val Order = 0
//			val ItemIDs = intArrayOf(1, 2)
//
//			for (ItemID in ItemIDs) {
//				when (ItemID) {
//					1 -> M!!.add(GroupID, ItemID, Order, "编辑")
//					2 -> M!!.add(GroupID, ItemID, Order, "删除")
//				}
//			}
//		}

		override fun onCreateContextMenu(M: ContextMenu?, V: View?, MenuInfo: ContextMenu.ContextMenuInfo?) {
			val Edit: MenuItem = M!!.add(Menu.NONE, 1, 1, "编辑")
			val Delete: MenuItem = M.add(Menu.NONE, 2, 2, "删除")
			Edit.setOnMenuItemClickListener(this)
			Delete.setOnMenuItemClickListener(this)
		}

		override fun onMenuItemClick(MItem: MenuItem): Boolean {
			when (MItem.itemId) {
				2 -> {
					DeleteBookItem(adapterPosition)
				}
			}
			return true
		}
	}

//	private var MContext: Context? = null
//	private var MPosition: Int = -1

	override fun onCreateViewHolder(Parent: ViewGroup, ViewType: Int): BookViewHolder {
//		if (MContext == null) MContext = Parent.context
		val ItemView = LayoutInflater.from(Parent.context).inflate(R.layout.item_layout, Parent, false)
		return BookViewHolder(ItemView)
	}

	override fun onBindViewHolder(Holder: BookViewHolder, Position: Int) {
		Holder.Bind(BookList[Position])
//		Holder.itemView.setOnLongClickListener {
//			MPosition = Holder.layoutPosition
//			false
//		}
	}

//	override fun onViewRecycled(Holder: BookViewHolder) {
//		Holder.itemView.setOnLongClickListener(null)
//		super.onViewRecycled(Holder)
//	}

	override fun getItemCount() = BookList.size

	fun DeleteBookItem(Pos: Int) {
		BookList.removeAt(Pos)
		notifyItemRemoved(Pos)
	}
}