package top.abr.androidexp6

import androidx.annotation.DrawableRes
import java.io.*
import java.nio.ByteBuffer
import java.security.MessageDigest

class Book constructor(@DrawableRes var CoverResourceID: Int = 0, var Title: String = "") : Serializable {
	companion object {
		val serialVersionUID: Long

		init {
			val Hasher = MessageDigest.getInstance("SHA-512")
			Hasher.update("top.abr.androidexp6.Book-2021-11-05".toByteArray())
			serialVersionUID = ByteBuffer.wrap(Hasher.digest()).long
		}

		fun ReadBookList(SerializedFile: String, A: BooksAdapter) {
			val BookListIStream = ObjectInputStream(FileInputStream(SerializedFile))
			A.BookList.clear()
			var B: Book
			var HasNext = true
			while (HasNext) {
				B = BookListIStream.readObject() as Book
				if (B != null) A.BookList.add(B)
				else HasNext = false
			}
		}

		fun SaveBookList(SerializedFile: String) {
			val BookListOStream = ObjectOutputStream(FileOutputStream(SerializedFile))
		}
	}
}