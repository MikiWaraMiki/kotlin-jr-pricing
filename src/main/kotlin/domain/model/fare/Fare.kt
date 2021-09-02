package domain.model.fare

/**
 * 運賃クラス
 */
class Fare(aValue: Int) {
    val value: Int

    init {
        if (aValue % 10 != 0) {
            throw IllegalArgumentException("運賃は10円単位である必要があります")
        }

        value = aValue
    }
}