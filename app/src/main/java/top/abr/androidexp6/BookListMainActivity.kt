package top.abr.androidexp6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
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

	lateinit var BookListView: RecyclerView

	override fun onCreate(SavedInstanceState: Bundle?) {
		super.onCreate(SavedInstanceState)
		setContentView(R.layout.activity_main)

		BookListView = findViewById(R.id.recycle_view_books)
		BookListView.adapter = BooksAdapter(BookList)
		BookListView.layoutManager = LinearLayoutManager(this)
	}
}