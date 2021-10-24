package top.abr.androidexp6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookListMainActivity : AppCompatActivity() {
	val BookList = ArrayList<Book>(
		arrayListOf(
			Book(R.drawable.book_2, "软件项目管理案例教程（第4版）"),
			Book(R.drawable.book_no_name, "创新工程实践"),
			Book(R.drawable.book_1, "信息安全数学基础（第2版）")
		)
	)

//	lateinit var BookListView: RecyclerView
	lateinit var BookListView: RecyclerViewWithContextMenu

	override fun onCreate(SavedInstanceState: Bundle?) {
		super.onCreate(SavedInstanceState)
		setContentView(R.layout.activity_main)

		BookListView = findViewById(R.id.recycle_view_books)
		registerForContextMenu(BookListView)
		BookListView.adapter = BooksAdapter(BookList)
		BookListView.layoutManager = LinearLayoutManager(this)
	}

	override fun onCreateContextMenu(M: ContextMenu?, V: View?, MenuInfo: ContextMenu.ContextMenuInfo?) {
		val GroupID = Menu.NONE
		val Order = Menu.NONE
		val ItemIDs = intArrayOf(1, 2)

		for (ItemID in ItemIDs) {
			when (ItemID) {
				1 -> M!!.add(GroupID, ItemID, Order, "编辑")
				2 -> M!!.add(GroupID, ItemID, Order, "删除")
			}
		}
	}

	override fun onContextItemSelected(MItem: MenuItem): Boolean {
		when (MItem.itemId) {
			2 -> {
				val MainBooksAdapter = BookListView.adapter as BooksAdapter
				MainBooksAdapter.DeleteBookItem(BookListView.ContextInfo.Position)
//				MainBooksAdapter.DeleteBookItem(MainBooksAdapter.MPosition)
			}
		}
		return super.onContextItemSelected(MItem)
	}
}