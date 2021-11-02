package top.abr.androidexp6

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.abr.androidexp6.databinding.ActivityMainBinding

class BookListMainActivity : AppCompatActivity() {
	open inner class EditBookInformation : ActivityResultContract<Pair<Book, Bundle>, Pair<Book, Bundle>>() {
		override fun createIntent(AppContext: Context, Inputs: Pair<Book, Bundle>) =
			Intent(this@BookListMainActivity, EditBookActivity::class.java).apply {
				putExtra("Book.Title", Inputs.first.Title)
				putExtras(Inputs.second)
			}

		override fun parseResult(ResultCode: Int, IntentWithResult: Intent?): Pair<Book, Bundle>? {
			if (ResultCode != Activity.RESULT_OK) return null
			Toast.makeText(this@BookListMainActivity, "<BookListMainActivity>" + IntentWithResult?.getStringExtra("Book.Title")!!, Toast.LENGTH_SHORT).show()
			return Pair(Book(Title = IntentWithResult?.getStringExtra("Book.Title")!!), IntentWithResult.extras!!)
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
	lateinit var EditBookActivityLauncher: ActivityResultLauncher<Pair<Book, Bundle>>

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
			if (it != null)
				when (it.second.getString("Mode")) {
					"New" -> MainBooksAdapter.AddBookItem(Book(R.drawable.book_no_name, it.first.Title))
					"Edit" -> MainBooksAdapter.ModifyBookItem(MainBooksAdapter.MPosition, Title = it.first.Title)
				}
		}
	}

	override fun onContextItemSelected(MItem: MenuItem): Boolean {
		when (MItem.title) {
			"新建" -> {
				val EditParam = Bundle()
				EditParam.putString("Mode", "New")
				EditBookActivityLauncher.launch(Pair(Book(), EditParam))
			}
			"编辑" -> {
				val EditParam = Bundle()
				EditParam.putString("Mode", "Edit")
				EditBookActivityLauncher.launch(Pair(MainBooksAdapter.BookList[MainBooksAdapter.MPosition], EditParam))
			}
			"删除" -> {
				MainBooksAdapter.DeleteBookItem(MainBooksAdapter.MPosition)
			}
		}
		return super.onContextItemSelected(MItem)
	}
}