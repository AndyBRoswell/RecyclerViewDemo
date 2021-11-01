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
import top.abr.androidexp6.databinding.ActivityMainBinding

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

	lateinit var ActivityMain: ActivityMainBinding
	lateinit var BookListView: RecyclerView
	lateinit var MainBooksAdapter: BooksAdapter
	lateinit var EditBookActivityLauncher: ActivityResultLauncher<Book>

	override fun onCreate(SavedInstanceState: Bundle?) {
		super.onCreate(SavedInstanceState)

		ActivityMain = ActivityMainBinding.inflate(layoutInflater)
		setContentView(ActivityMain.root)
//		setContentView(R.layout.activity_main)

//		BookListView = findViewById(R.id.recycle_view_books)
		BookListView = ActivityMain.recycleViewBooks
		BookListView.adapter = BooksAdapter(BookList)
		BookListView.layoutManager = LinearLayoutManager(this)

		MainBooksAdapter = BookListView.adapter as BooksAdapter
		EditBookActivityLauncher = registerForActivityResult(EditBookInformation()) {
			if (it != null) MainBooksAdapter.ModifyBookItem(MainBooksAdapter.MPosition, Title = it.Title)
		}
	}

	override fun onContextItemSelected(MItem: MenuItem): Boolean {
		when (MItem.title) {
			"新建" -> {
				EditBookActivityLauncher.launch(Book())
			}
			"编辑" -> {
				EditBookActivityLauncher.launch(MainBooksAdapter.BookList[MainBooksAdapter.MPosition])
			}
			"删除" -> {
				MainBooksAdapter.DeleteBookItem(MainBooksAdapter.MPosition)
			}
		}
		return super.onContextItemSelected(MItem)
	}
}