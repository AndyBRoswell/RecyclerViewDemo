package top.abr.androidexp6

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.view.MenuCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.abr.androidexp6.databinding.ActivityBookListBinding

class BookListActivity : AppCompatActivity() {
	lateinit var ActivityBookList: ActivityBookListBinding
	lateinit var BookListView: RecyclerView
	lateinit var MainBooksAdapter: BooksAdapter
	lateinit var EditBookActivityLauncher: ActivityResultLauncher<Pair<Book, Bundle>>

	lateinit var InternalFilesDir: String
	lateinit var ExternalFilesDir: String
	lateinit var DefaultInternalBookListFile: String
	lateinit var DefaultExternalBookListFile: String

	open inner class EditBookInformation : ActivityResultContract<Pair<Book, Bundle>, Pair<Book, Bundle>>() {
		override fun createIntent(AppContext: Context, Inputs: Pair<Book, Bundle>) =
			Intent(this@BookListActivity, EditBookActivity::class.java).apply {
				putExtra("Book.Title", Inputs.first.Title)
				putExtra("EditParam", Inputs.second)
			}

		override fun parseResult(ResultCode: Int, IntentWithResult: Intent?): Pair<Book, Bundle>? {
			if (ResultCode != Activity.RESULT_OK) return null
			return Pair(Book(Title = IntentWithResult?.getStringExtra("Book.Title")!!), IntentWithResult.getBundleExtra("EditParam")!!)
		}
	}

	override fun onCreate(SavedInstanceState: Bundle?) {
		super.onCreate(SavedInstanceState)

		InternalFilesDir = applicationContext.filesDir.toString()
		DefaultInternalBookListFile = "$InternalFilesDir/BookList.txt"
		ExternalFilesDir = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) applicationContext.getExternalFilesDir(null).toString() else ""
		DefaultExternalBookListFile = if (ExternalFilesDir != "") "$ExternalFilesDir/BookList.txt" else ""

		ActivityBookList = ActivityBookListBinding.inflate(layoutInflater)
		setContentView(ActivityBookList.root)

		BookListView = ActivityBookList.recycleViewBooks
		BookListView.adapter = BooksAdapter()
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

	override fun onCreateOptionsMenu(M: Menu?): Boolean {
		menuInflater.inflate(R.menu.book_list_options, M)
		MenuCompat.setGroupDividerEnabled(M, true)
		return true
	}

	override fun onOptionsItemSelected(MItem: MenuItem): Boolean {
		when (MItem.itemId) {
			R.id.MainOptionsReadInternally -> StorageAccessor.ReadBookList(MainBooksAdapter, DefaultInternalBookListFile)
			R.id.MainOptionsReadExternally -> if (DefaultExternalBookListFile != "") StorageAccessor.ReadBookList(MainBooksAdapter, DefaultExternalBookListFile)
			R.id.MainOptionsClearBookList -> MainBooksAdapter.ClearBookList()
			R.id.MainOptionsSaveInternally -> StorageAccessor.SaveBookList(MainBooksAdapter, DefaultInternalBookListFile)
			R.id.MainOptionsSaveExternally -> if (DefaultExternalBookListFile != "") StorageAccessor.SaveBookList(MainBooksAdapter, DefaultExternalBookListFile)
		}
		Toast.makeText(this, MItem.title.toString() + "  成功", Toast.LENGTH_SHORT).show()
		return true
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