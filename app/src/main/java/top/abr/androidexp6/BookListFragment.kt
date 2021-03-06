package top.abr.androidexp6

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.core.view.MenuCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.abr.androidexp6.databinding.ActivityBookListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookListFragment.newInstance] factory method to create an instance of this fragment.
 */
class BookListFragment : Fragment() {
	// TODO: Rename and change types of parameters
	private var param1: String? = null
	private var param2: String? = null

	lateinit var FragmentBookList: ActivityBookListBinding
	lateinit var BookListView: RecyclerView
	lateinit var MainBooksAdapter: BooksAdapter
	lateinit var EditBookActivityLauncher: ActivityResultLauncher<Pair<Book, Bundle>>

	lateinit var InternalFilesDir: String
	lateinit var ExternalFilesDir: String
	lateinit var DefaultInternalBookListFile: String
	lateinit var DefaultExternalBookListFile: String

	open inner class EditBookInformation : ActivityResultContract<Pair<Book, Bundle>, Pair<Book, Bundle>>() {
		override fun createIntent(AppContext: Context, Inputs: Pair<Book, Bundle>) =
			Intent(this@BookListFragment.activity, EditBookActivity::class.java).apply {
				val BookInformation = bundleOf("Title" to Inputs.first.Title)
				putExtra("Book", BookInformation)
				putExtra("EditParam", Inputs.second)
			}

		override fun parseResult(ResultCode: Int, IntentWithResult: Intent?): Pair<Book, Bundle>? {
			if (ResultCode != Activity.RESULT_OK) return null
			val BookInformation = IntentWithResult!!.getBundleExtra("Book")!!
			return Pair(Book(Title = BookInformation.getString("Title")!!), IntentWithResult.getBundleExtra("EditParam")!!)
		}
	}

	override fun onCreate(SavedInstanceState: Bundle?) {
		super.onCreate(SavedInstanceState)
		arguments?.let {
			param1 = it.getString(ARG_PARAM1)
			param2 = it.getString(ARG_PARAM2)
		}

		InternalFilesDir = activity?.applicationContext?.filesDir.toString()
		DefaultInternalBookListFile = "$InternalFilesDir/BookList.txt"
		ExternalFilesDir = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) activity?.applicationContext?.getExternalFilesDir(null).toString() else ""
		DefaultExternalBookListFile = if (ExternalFilesDir != "") "$ExternalFilesDir/BookList.txt" else ""
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment (corresponding to setContentView in onCreate method of an Activity)
		return inflater.inflate(R.layout.activity_book_list, container, false)
	}

	override fun onViewCreated(V: View, SavedInstanceState: Bundle?) {
		super.onViewCreated(V, SavedInstanceState)

		BookListView = V.findViewById(R.id.recycle_view_books)
		BookListView.adapter = BooksAdapter()
		BookListView.layoutManager = LinearLayoutManager(this.activity)

		FragmentBookList = ActivityBookListBinding.inflate(layoutInflater)
		setHasOptionsMenu(true)

		MainBooksAdapter = BookListView.adapter as BooksAdapter
		activity?.supportFragmentManager?.setFragmentResultListener("EditBookResult", this) { _, ResultBundle ->
			if (ResultBundle.size() != 0) {
				val BookInformation = ResultBundle.getBundle("Book")!!
				val EditParam = ResultBundle.getBundle("EditParam")!!
				when (EditParam.getString("Mode")) {
					"New" -> MainBooksAdapter.AddBookItem(Book(R.drawable.book_no_name, BookInformation.getString("Title")!!))
					"Edit" -> MainBooksAdapter.ModifyBookItem(MainBooksAdapter.MPosition, Title = BookInformation.getString("Title"))
				}
			}
		}
		EditBookActivityLauncher = registerForActivityResult(EditBookInformation()) {
			if (it != null)
				when (it.second.getString("Mode")) {
					"New" -> MainBooksAdapter.AddBookItem(Book(R.drawable.book_no_name, it.first.Title))
					"Edit" -> MainBooksAdapter.ModifyBookItem(MainBooksAdapter.MPosition, Title = it.first.Title)
				}
		}
	}

	override fun onCreateOptionsMenu(M: Menu, I: MenuInflater) {
		I.inflate(R.menu.book_list_options, M)
		MenuCompat.setGroupDividerEnabled(M, true)
	}

	override fun onOptionsItemSelected(MItem: MenuItem): Boolean {
		when (MItem.itemId) {
			R.id.MainOptionsReadInternally -> StorageAccessor.ReadBookList(MainBooksAdapter, DefaultInternalBookListFile)
			R.id.MainOptionsReadExternally -> if (DefaultExternalBookListFile != "") StorageAccessor.ReadBookList(MainBooksAdapter, DefaultExternalBookListFile)
			R.id.MainOptionsClearBookList -> MainBooksAdapter.ClearBookList()
			R.id.MainOptionsSaveInternally -> StorageAccessor.SaveBookList(MainBooksAdapter, DefaultInternalBookListFile)
			R.id.MainOptionsSaveExternally -> if (DefaultExternalBookListFile != "") StorageAccessor.SaveBookList(MainBooksAdapter, DefaultExternalBookListFile)
		}
		Toast.makeText(this.activity, MItem.title.toString() + "  ??????", Toast.LENGTH_SHORT).show()
		return true
	}

	override fun onContextItemSelected(MItem: MenuItem): Boolean {
		when (MItem.title) {
			"??????" -> {
				val EditParam = Bundle()
				EditParam.putString("Mode", "New")
				EditBookActivityLauncher.launch(Pair(Book(), EditParam))
			}
			"??????" -> {
				val EditParam = Bundle()
				EditParam.putString("Mode", "Edit")
				EditBookActivityLauncher.launch(Pair(MainBooksAdapter.BookList[MainBooksAdapter.MPosition], EditParam))
			}
			"??????" -> {
				MainBooksAdapter.DeleteBookItem(MainBooksAdapter.MPosition)
			}
		}
		return super.onContextItemSelected(MItem)
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment BookListFragment.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance(param1: String, param2: String) =
			BookListFragment().apply {
				arguments = Bundle().apply {
					putString(ARG_PARAM1, param1)
					putString(ARG_PARAM2, param2)
				}
			}
	}
}