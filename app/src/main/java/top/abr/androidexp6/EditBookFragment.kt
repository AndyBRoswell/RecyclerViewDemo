package top.abr.androidexp6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import top.abr.androidexp6.databinding.FragmentEditBookBinding

/**
 * A simple [Fragment] subclass.
 * Use the [EditBookFragment.newInstance] factory method to create an instance of this fragment.
 */
class EditBookFragment : Fragment() {
	open inner class EditBookEventHandlers {
		fun OnClickButtonOK(V: View) {
			val BookInformation = bundleOf(
				"Title" to FragmentEditBook.editBookTitle.text.toString()
			)
			activity?.supportFragmentManager?.setFragmentResult(
				"EditBookResult",
				bundleOf(
					"Book" to BookInformation,
					"EditParam" to arguments?.getBundle("EditParam")
				)
			)
			activity?.supportFragmentManager?.popBackStackImmediate()
		}

		fun OnClickButtonCancel(V: View) {
			activity?.supportFragmentManager?.setFragmentResult("EditBookResult", Bundle())
			activity?.supportFragmentManager?.popBackStackImmediate()
		}
	}

	private var Params: Bundle? = null

	lateinit var FragmentEditBook: FragmentEditBookBinding

	override fun onCreate(SavedInstanceState: Bundle?) {
		super.onCreate(SavedInstanceState)

		FragmentEditBook = FragmentEditBookBinding.inflate(layoutInflater)

		FragmentEditBook.handlers = EditBookEventHandlers()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_edit_book, container, false)
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of this fragment using the provided parameters.
		 *
		 * @return A new instance of fragment EditBookFragment.
		 */
		@JvmStatic
		fun newInstance(Params: Bundle) =
			EditBookFragment().apply {
				arguments = Bundle().apply {

				}
			}
	}
}