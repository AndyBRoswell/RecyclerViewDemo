package top.abr.androidexp6

import java.math.BigDecimal
import java.math.BigInteger

class Coord2D<T>(var x: T, var y: T) {
    init {
        when (x) {
            is Number, is BigInteger, is BigDecimal -> {}
            else -> throw IllegalArgumentException("不支持的泛型参数：必须是有符号的数值类型或 BigInteger 或 BigDecimal。")
        }
    }
}