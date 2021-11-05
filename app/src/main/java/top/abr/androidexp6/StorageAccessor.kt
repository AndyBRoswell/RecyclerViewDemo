package top.abr.androidexp6

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

open class StorageAccessor {
	companion object {
		fun ReadBookList(A: BooksAdapter, SerializedFile: String = "BookList.txt") {
			val BookListIStream = ObjectInputStream(FileInputStream(SerializedFile))
			A.BookList.clear()
			var B: Book
			var HasNext = true
			while (HasNext) {
				B = BookListIStream.readObject() as Book
				if (B != null) A.BookList.add(B)
				else HasNext = false
			}
			BookListIStream.close()
			A.notifyItemChanged(0)
		}

		fun SaveBookList(A: BooksAdapter, SerializedFile: String = "BookList.txt") {
			val BookListOStream = ObjectOutputStream(FileOutputStream(SerializedFile))
			for (B in A.BookList) {
				BookListOStream.writeObject(B)
			}
			BookListOStream.writeObject(null)
			BookListOStream.close()
		}
	}
}