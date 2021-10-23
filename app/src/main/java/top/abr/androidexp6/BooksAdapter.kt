package top.abr.androidexp6

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

open class BooksAdapter(var BookList: ArrayList<Book>) : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {
	class BookViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
		val BookCoverView: ImageView = ItemView.findViewById(R.id.image_view_book_cover)
		val BookTitleView: TextView = ItemView.findViewById(R.id.text_view_book_title)

		fun Bind(B: Book) {
			BookCoverView.setImageResource(B.CoverResourceID)
			BookTitleView.text = B.Title
		}
	}

	private lateinit var MContext: Context

	override fun onCreateViewHolder(Parent: ViewGroup, ViewType: Int): BookViewHolder {
		if (MContext == null) MContext = Parent.context
		val ItemView = LayoutInflater.from(Parent.context).inflate(R.layout.item_layout, Parent, false)
		return BookViewHolder(ItemView)
	}

	override fun onBindViewHolder(Holder: BookViewHolder, Position: Int) {
		Holder.Bind(BookList[Position])
	}

	override fun getItemCount() = BookList.size
}