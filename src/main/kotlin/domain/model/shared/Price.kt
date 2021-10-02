package domain.model.shared

import lib.domainsupport.valueobject.ValueObject
import java.lang.IllegalArgumentException

/**
 * 料金計算で利用する金額クラス
 * 10円単位の金額のみ許容する
 */
class Price: ValueObject<Int>  {

    constructor(aValue: Int): super(aValue) {
        if (aValue < 1) {
            throw IllegalArgumentException("金額は1円以上である必要があります")
        }

        if (aValue % 10 > 0) {
            throw IllegalArgumentException("金額は10円単位である必要があります")
        }
    }

    companion object {
        fun of(aValue: Int): Price {
            val surplus = aValue % 10

            if (surplus == 0) return Price(aValue)

            return Price(aValue - surplus)
        }
    }
}