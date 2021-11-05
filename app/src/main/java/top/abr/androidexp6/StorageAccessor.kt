package top.abr.androidexp6

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.NullPointerException

open class StorageAccessor {
	companion object {
		fun ReadBookList(A: BooksAdapter, SerializedFile: String) {
			val BookListIStream = ObjectInputStream(FileInputStream(SerializedFile))
			A.BookList.clear()
			var B: Book
			while (true) {
				try {
					B = BookListIStream.readObject() as Book
					A.BookList.add(B)
				}
				catch (E: NullPointerException) { break; }
			}
			BookListIStream.close()
			A.notifyItemChanged(0)
		}

		fun SaveBookList(A: BooksAdapter, TargetedFile: String) {
			val BookListOStream = ObjectOutputStream(FileOutputStream(TargetedFile))
			for (B in A.BookList) {
				BookListOStream.writeObject(B)
			}
			BookListOStream.writeObject(null)
			BookListOStream.close()
		}
	}
}