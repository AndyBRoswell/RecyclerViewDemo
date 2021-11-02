package top.abr.androidexp6

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import top.abr.androidexp6.databinding.ActivityEditBookBinding

class EditBookActivity : AppCompatActivity() {
	open inner class EditBookEventHandlers {
		fun OnClickButtonOK(V: View) {
			val IntentWithResult = Intent().apply {
				putExtra("Book.Title", ActivityEditBook.editBookTitle.text.toString())
				putExtras(intent.extras!!)
			}
			setResult(Activity.RESULT_OK, IntentWithResult)
			finish()
		}

		fun OnClickButtonCancel(V: View) {
			setResult(Activity.RESULT_CANCELED)
			finish()
		}
	}

	lateinit var ActivityEditBook: ActivityEditBookBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		ActivityEditBook = ActivityEditBookBinding.inflate(layoutInflater)
		setContentView(ActivityEditBook.root)

		ActivityEditBook.handlers = EditBookEventHandlers()

		ActivityEditBook.editBookTitle.setText(intent.getStringExtra("Book.Title"))
	}
}