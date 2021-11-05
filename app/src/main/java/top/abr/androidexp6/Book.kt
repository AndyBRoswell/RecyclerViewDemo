package top.abr.androidexp6

import androidx.annotation.DrawableRes
import java.io.Serializable
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
	}
}