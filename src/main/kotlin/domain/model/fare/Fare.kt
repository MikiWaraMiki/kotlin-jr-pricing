package domain.model.fare

import domain.model.shared.Price

/**
 * 運賃クラス
 */
class Fare(aPrice: Price, isChild: Boolean) {
    private val CHILD_DISCOUNT_RATE = 0.5

    val price: Price

    init {
        price = if (isChild)
            childPrice(aPrice)
        else
            aPrice
    }

    private fun childPrice(basePrice: Price): Price {
        val discountedPrice = (basePrice.value * CHILD_DISCOUNT_RATE).toInt()

        // 1円単位・5円単位の端数は切り捨て
        val truncated = discountedPrice % 10
        return Price(discountedPrice - truncated)
    }
}