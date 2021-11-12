package top.abr.androidexp6

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import top.abr.androidexp6.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {
	open inner class PageAdapter(F: FragmentActivity) : FragmentStateAdapter(F) {
		override fun getItemCount(): Int = HOME_TAB_COUNT

		override fun createFragment(Pos: Int): Fragment {
			when (Pos) {
				0 -> {
				}
				else -> {

				}
			}
		}
	}

	val HOME_TAB_COUNT = 3

	private lateinit var ActivityMain: ActivityMainBinding
	private lateinit var Home: ViewPager2

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		ActivityMain = ActivityMainBinding.inflate(layoutInflater)
		Home = ActivityMain.Home
	}
}