package top.abr.androidexp6

import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView

open class BooksAdapter(var BookList: ArrayList<Book>) : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {
	open inner class BookViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), View.OnCreateContextMenuListener/*, MenuItem.OnMenuItemClickListener*/ {
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
			val MenuItemHeaders = arrayOf("编辑", "删除")
			for (i in MenuItemHeaders.indices) {
				M!!.add(Menu.NONE, i, i, MenuItemHeaders[i])
			}
		}
	}

	var MPosition = -1

	override fun onCreateViewHolder(Parent: ViewGroup, ViewType: Int): BookViewHolder {
		val ItemView = LayoutInflater.from(Parent.context).inflate(R.layout.item_layout, Parent, false)
		return BookViewHolder(ItemView)
	}

	override fun onBindViewHolder(Holder: BookViewHolder, Position: Int) {
		Holder.Bind(BookList[Position])
		Holder.itemView.setOnLongClickListener {
			MPosition = Holder.layoutPosition
			false
		}
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