package top.abr.androidexp6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.LogoPosition
import com.baidu.mapapi.map.MapView
import top.abr.androidexp6.databinding.FragmentMapViewBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapViewFragment.newInstance] factory method to create an instance of this fragment.
 */
class MapViewFragment : Fragment() {
	// TODO: Rename and change types of parameters
	private var param1: String? = null
	private var param2: String? = null

	lateinit var FragmentMapView: FragmentMapViewBinding
	lateinit var BaiduMapView: MapView
	lateinit var MBaiduMap: BaiduMap

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			param1 = it.getString(ARG_PARAM1)
			param2 = it.getString(ARG_PARAM2)
		}

		SDKInitializer.initialize(this.activity)
		SDKInitializer.setCoordType(CoordType.BD09LL)
	}

	override fun onCreateView(LI: LayoutInflater, Container: ViewGroup?, SavedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		FragmentMapView = FragmentMapViewBinding.inflate(LI, Container, false)
		return FragmentMapView.root
	}

	override fun onViewCreated(V: View, SavedInstanceState: Bundle?) {
		BaiduMapView = FragmentMapView.BaiduMapView
		MBaiduMap = BaiduMapView.map
		MBaiduMap.mapType = BaiduMap.MAP_TYPE_NORMAL
		BaiduMapView.logoPosition = LogoPosition.logoPostionCenterTop
	}

	override fun onResume() {
		super.onResume()
		BaiduMapView.onResume()
	}

	override fun onPause() {
		super.onPause()
		BaiduMapView.onPause()
	}

	override fun onDestroy() {
		super.onDestroy()
		BaiduMapView.onDestroy()
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment MapViewFragment.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic fun newInstance(param1: String, param2: String) =
			MapViewFragment().apply {
				arguments = Bundle().apply {
					putString(ARG_PARAM1, param1)
					putString(ARG_PARAM2, param2)
				}
			}
	}
}