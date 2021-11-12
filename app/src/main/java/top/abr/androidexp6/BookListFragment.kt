package top.abr.androidexp6

import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
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
 * Use the [BookListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookListFragment : Fragment() {
	// TODO: Rename and change types of parameters
	private var param1: String? = null
	private var param2: String? = null

	lateinit var FragmentBookList: ActivityBookListBinding
	lateinit var BookListView: RecyclerView
	lateinit var MainBooksAdapter: BooksAdapter

	lateinit var InternalFilesDir: String
	lateinit var ExternalFilesDir: String
	lateinit var DefaultInternalBookListFile: String
	lateinit var DefaultExternalBookListFile: String

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

		FragmentBookList = ActivityBookListBinding.inflate(layoutInflater)
		setHasOptionsMenu(true)

		BookListView = FragmentBookList.recycleViewBooks
		BookListView.adapter = BooksAdapter()
		BookListView.layoutManager = LinearLayoutManager(this.activity)

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
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.activity_book_list, container, false)
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
		Toast.makeText(this.activity, MItem.title.toString() + "  成功", Toast.LENGTH_SHORT).show()
		return true
	}

	fun LaunchEditBookFragment(B: Book, EditParam: Bundle?) {
		val BookInformation = bundleOf("Title" to B.Title)
		val Params: Bundle = bundleOf("Book" to BookInformation, "EditParam" to EditParam)
		activity?.supportFragmentManager?.beginTransaction()?.apply {
			replace(((view as ViewGroup).parent as View).id, EditBookFragment.newInstance(Params))
			addToBackStack(null)
			commit()
		}
	}

	override fun onContextItemSelected(MItem: MenuItem): Boolean {
		when (MItem.title) {
			"新建" -> {
				val EditParam = Bundle()
				EditParam.putString("Mode", "New")
				LaunchEditBookFragment(Book(), EditParam)
			}
			"编辑" -> {
				val EditParam = Bundle()
				EditParam.putString("Mode", "Edit")
				LaunchEditBookFragment(MainBooksAdapter.BookList[MainBooksAdapter.MPosition], EditParam)
			}
			"删除" -> {
				MainBooksAdapter.DeleteBookItem(MainBooksAdapter.MPosition)
			}
		}
		return super.onContextItemSelected(MItem)
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
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