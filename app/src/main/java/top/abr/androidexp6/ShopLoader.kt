package top.abr.androidexp6

import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.math.*

open class Shop(Name: String = "", Longitude: Double = 0.0, Latitude: Double = 0.0, Memo: String = "") {
    var Name: String = ""
    var Longitude: Double = 0.0
    var Latitude: Double = 0.0
    var Memo: String = ""

    init {
        if ((abs(Longitude) > 180.0) or (abs(Latitude) > 90.0)) throw IllegalArgumentException()
    }
}

open class ShopLoader {
    companion object {
        fun download(): String {
            val ShopListURL = URL("http://file.nidama.net/class/mobile_develop/data/bookstore.json")
            val URLContentReader = BufferedReader(InputStreamReader(ShopListURL.openStream()))
            val URLContentBuilder = StringBuilder()
            while (true) {
                val CurrentLine = URLContentReader.readLine()
                if (CurrentLine != null) URLContentBuilder.append(CurrentLine)
                else break
            }
            return URLContentBuilder.toString()
        }

        fun parsonJson(JSONString: String): ArrayList<Shop> {
            val ShopList = ArrayList<Shop>()
            val ShopData = JSONArray(JSONString)
            for (i in 0 until ShopData.length()) {
                val ShopDataItem = ShopData.getJSONObject(i)
                ShopList.add(Shop(
                    ShopDataItem.getString("name"),
                    ShopDataItem.getDouble("longitude"),
                    ShopDataItem.getDouble("latitude"),
                    ShopDataItem.getString("memo")
                ))
            }
            return ShopList
        }
    }
}