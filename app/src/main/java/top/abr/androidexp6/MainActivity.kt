package top.abr.androidexp6

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import top.abr.androidexp6.databinding.ActivityMainBinding

open class PageAdapter(FAWithViewPager2: FragmentActivity, val TabCount: Int) : FragmentStateAdapter(FAWithViewPager2) {
	override fun getItemCount(): Int = TabCount

	override fun createFragment(Pos: Int): Fragment {
		return when (Pos) {
			0 -> BookListFragment()
			1 -> MessageFragment()
			else -> DemoFragment()
		}
	}
}

class MainActivity : AppCompatActivity() {
	val HOME_TAB_COUNT = 3
	val TabTitle = arrayOf("图书", "新闻", "卖家")

	lateinit var ActivityMain: ActivityMainBinding
	lateinit var TabSelector: TabLayout
	lateinit var TabDisplayer: ViewPager2

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		ActivityMain = ActivityMainBinding.inflate(layoutInflater)
		TabSelector = ActivityMain.TabSelector
		TabDisplayer = ActivityMain.TabDisplayer
		setContentView(ActivityMain.root)

		TabDisplayer.adapter = PageAdapter(this, HOME_TAB_COUNT)

		TabLayoutMediator(TabSelector, TabDisplayer) { CurrentTab, CurrentPos ->
			CurrentTab.text = TabTitle[CurrentPos]
		}.attach()
	}
}