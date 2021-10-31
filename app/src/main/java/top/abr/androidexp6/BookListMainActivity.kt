package top.abr.androidexp6

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookListMainActivity : AppCompatActivity() {
	open inner class EditBookInformation : ActivityResultContract<Book, Book>() {
		override fun createIntent(AppContext: Context, BookForEdit: Book) =
			Intent(this@BookListMainActivity, EditBookActivity::class.java).apply {
				putExtra("Book.Title", BookForEdit.Title)
			}

		override fun parseResult(ResultCode: Int, IntentWithResult: Intent?): Book? {
			if (ResultCode != Activity.RESULT_OK) return null
			return Book(Title = IntentWithResult?.getStringExtra("Book.Title")!!)
		}
	}

	val BookList = ArrayList<Book>(
		arrayListOf(
			Book(R.drawable.book_2, "软件项目管理案例教程（第4版）"),
			Book(R.drawable.book_no_name, "创新工程实践"),
			Book(R.drawable.book_1, "信息安全数学基础（第2版）")
		)
	)

	lateinit var BookListView: RecyclerView

	override fun onCreate(SavedInstanceState: Bundle?) {
		super.onCreate(SavedInstanceState)
		setContentView(R.layout.activity_main)

		BookListView = findViewById(R.id.recycle_view_books)
		BookListView.adapter = BooksAdapter(BookList)
		BookListView.layoutManager = LinearLayoutManager(this)
	}

	override fun onContextItemSelected(MItem: MenuItem): Boolean {
		val MainBooksAdapter = BookListView.adapter as BooksAdapter
		when (MItem.itemId) {
			0 -> {
				val EditBookActivityLauncher = registerForActivityResult(EditBookInformation()) {
					BookList[MainBooksAdapter.MPosition].Title = it.Title
				}.launch(MainBooksAdapter.BookList[MainBooksAdapter.MPosition])
			}
			1 -> {
				MainBooksAdapter.DeleteBookItem(MainBooksAdapter.MPosition)
			}
		}
		return super.onContextItemSelected(MItem)
	}
}