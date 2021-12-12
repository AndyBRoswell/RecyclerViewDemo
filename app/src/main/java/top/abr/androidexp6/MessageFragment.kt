package top.abr.androidexp6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import top.abr.androidexp6.databinding.FragmentMessageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MessageFragment.newInstance] factory method to create an instance of this fragment.
 */
class MessageFragment : Fragment() {
	// TODO: Rename and change types of parameters
	private var param1: String? = null
	private var param2: String? = null

	val DefaultURL = "https://news.sina.cn"

	lateinit var FragmentMessage: FragmentMessageBinding
	lateinit var WebpageView: WebView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			param1 = it.getString(ARG_PARAM1)
			param2 = it.getString(ARG_PARAM2)
		}
	}

	override fun onCreateView(LI: LayoutInflater, Container: ViewGroup?, SavedInstanceState: Bundle?): View {
		// Inflate the layout for this fragment
		FragmentMessage = FragmentMessageBinding.inflate(LI, Container, false)
		return FragmentMessage.root
	}

	override fun onViewCreated(V: View, SavedInstanceState: Bundle?) {
		super.onViewCreated(V, SavedInstanceState)

		WebpageView = FragmentMessage.WebpageView
		WebpageView.webViewClient = object : WebViewClient() {
			override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
				return false
			}
		}
		WebpageView.settings.javaScriptEnabled = true
		WebpageView.loadUrl(DefaultURL)
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment MessageFragment.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic fun newInstance(param1: String, param2: String) =
			MessageFragment().apply {
				arguments = Bundle().apply {
					putString(ARG_PARAM1, param1)
					putString(ARG_PARAM2, param2)
				}
			}
	}
}