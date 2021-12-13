package top.abr.androidexp6

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

open class ShopLoader {
    companion object {
        fun download() {
            val ShopListURL = URL("http://file.nidama.net/class/mobile_develop/data/bookstore.json")
            val URLContentReader = BufferedReader(InputStreamReader(ShopListURL.openStream()))
            
        }

        fun parsonJson() {

        }
    }
}