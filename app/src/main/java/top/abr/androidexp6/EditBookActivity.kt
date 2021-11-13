package top.abr.androidexp6

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import top.abr.androidexp6.databinding.ActivityEditBookBinding

class EditBookActivity : AppCompatActivity() {
	open inner class EditBookEventHandlers {
		fun OnClickButtonOK(V: View) {
			val IntentWithResult = Intent().apply {
				val BookInformation = bundleOf(
					"Title" to ActivityEditBook.editBookTitle.text.toString()
				)
				putExtra("EditParam", intent.getBundleExtra("EditParam"))
				putExtra("Book", BookInformation) // This statement MUST be put after the last statement. Or the string indexed by "Book.Title" will be overwritten.
			}
			setResult(Activity.RESULT_OK, IntentWithResult)
			finish()
		}

		fun OnClickButtonCancel(V: View) {
			onBackPressed()
		}
	}

	lateinit var ActivityEditBook: ActivityEditBookBinding

	override fun onCreate(SavedInstanceState: Bundle?) {
		super.onCreate(SavedInstanceState)

		ActivityEditBook = ActivityEditBookBinding.inflate(layoutInflater)
		setContentView(ActivityEditBook.root)

		ActivityEditBook.handlers = EditBookEventHandlers() // DO NOT FORGET THIS.

		val BookInformation = intent.getBundleExtra("Book") !!
		ActivityEditBook.editBookTitle.setText(BookInformation.getString("Title"))
	}

	override fun onBackPressed() {
		setResult(Activity.RESULT_CANCELED)
		finish()
	}
}