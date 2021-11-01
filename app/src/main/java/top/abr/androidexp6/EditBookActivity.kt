package top.abr.androidexp6

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import top.abr.androidexp6.databinding.ActivityEditBookBinding

class EditBookActivity : AppCompatActivity() {
	open inner class EditBookEventHandler {
		fun OnClickButtonOK(V: View) {

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

		ActivityEditBook.editBookTitle.setText(intent.getStringExtra("Book.Title"))
	}
}