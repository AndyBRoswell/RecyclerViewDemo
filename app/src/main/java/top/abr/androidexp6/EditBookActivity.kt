package top.abr.androidexp6

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import top.abr.androidexp6.databinding.ActivityEditBookBinding

class EditBookActivity : AppCompatActivity() {
	open inner class EditBookEventHandlers {
		fun OnClickButtonOK(V: View) {
			val IntentWithResult = Intent().apply {
				putExtra("Book.Title", ActivityEditBook.editBookTitle.text.toString())
				Toast.makeText(this@EditBookActivity, "<EditBookActivity>Mode = " + getStringExtra("Mode"), Toast.LENGTH_SHORT).show()
				putExtras(intent.extras!!)
			}
			setResult(Activity.RESULT_OK, IntentWithResult)
			Toast.makeText(this@EditBookActivity, "<EditBookActivity>" + ActivityEditBook.editBookTitle.text.toString(), Toast.LENGTH_SHORT).show()
			Toast.makeText(this@EditBookActivity, """<EditBookActivity>IntentWithResult.extras.getString("Book.Title") = """ + IntentWithResult.extras!!.getString("Book.Title"), Toast.LENGTH_SHORT).show()
			Toast.makeText(this@EditBookActivity, """<EditBookActivity>IntentWithResult.getStringExtra("Book.Title") = """ + IntentWithResult.getStringExtra("Book.Title"), Toast.LENGTH_SHORT).show()
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