package top.abr.androidexp6

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import top.abr.androidexp6.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {
	open inner class PageAdapter(F: FragmentActivity) : FragmentStateAdapter(F) {
		override fun getItemCount(): Int = HOME_TAB_COUNT

		override fun createFragment(Pos: Int): Fragment =
			when (Pos) {
				0 -> BookListFragment()
				else -> Fragment()
			}
	}

	val HOME_TAB_COUNT = 3
	val TabTitle = arrayOf("图书", "新闻", "卖家")

	lateinit var ActivityMain: ActivityMainBinding
	lateinit var Tabs : TabLayout
	lateinit var TabViewer: ViewPager2

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		ActivityMain = ActivityMainBinding.inflate(layoutInflater)
		Tabs = ActivityMain.Tabs
		TabViewer = ActivityMain.Home
		setContentView(ActivityMain.root)

		TabViewer.adapter = PageAdapter(this)

		TabLayoutMediator(Tabs, TabViewer) { CurrentTab, CurrentPos ->
			CurrentTab.text = TabTitle[CurrentPos]
		}.attach()
	}
}