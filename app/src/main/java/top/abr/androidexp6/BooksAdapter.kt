package top.abr.androidexp6

import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

open class BooksAdapter(var BookList: ArrayList<Book>) : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {
	open inner class BookViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
		val BookCoverView: ImageView = ItemView.findViewById(R.id.image_view_book_cover)
		val BookTitleView: TextView = ItemView.findViewById(R.id.text_view_book_title)

		init {
			ItemView.setOnCreateContextMenuListener(this)
		}

		fun Bind(B: Book) {
			BookCoverView.setImageResource(B.CoverResourceID)
			BookTitleView.text = B.Title
		}

		override fun onCreateContextMenu(M: ContextMenu?, V: View?, MenuInfo: ContextMenu.ContextMenuInfo?) {
			val Edit: MenuItem = M!!.add(Menu.NONE, 1, 1, "编辑")
			val Delete: MenuItem = M.add(Menu.NONE, 2, 2, "删除")
			Edit.setOnMenuItemClickListener(this)
			Delete.setOnMenuItemClickListener(this)
		}

		override fun onMenuItemClick(MItem: MenuItem): Boolean {
			when (MItem.itemId) {
				1 -> {

				}
				2 -> {
					DeleteBookItem(adapterPosition)
				}
			}
			return true
		}
	}

	override fun onCreateViewHolder(Parent: ViewGroup, ViewType: Int): BookViewHolder {
		val ItemView = LayoutInflater.from(Parent.context).inflate(R.layout.item_layout, Parent, false)
		return BookViewHolder(ItemView)
	}

	override fun onBindViewHolder(Holder: BookViewHolder, Position: Int) {
		Holder.Bind(BookList[Position])
	}

	override fun onViewRecycled(Holder: BookViewHolder) {
		Holder.itemView.setOnLongClickListener(null)
		super.onViewRecycled(Holder)
	}

	override fun getItemCount() = BookList.size

	fun DeleteBookItem(Pos: Int) {
		BookList.removeAt(Pos)
		notifyItemRemoved(Pos)
	}
}