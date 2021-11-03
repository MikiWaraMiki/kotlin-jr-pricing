package domain.model.fare

import domain.model.shared.Price

/**
 * 運賃クラス
 */
class Fare(aPrice: Price) {
    private val CHILD_DISCOUNT_RATE = 0.5

    private val price = aPrice

    fun price(isChild: Boolean): Price {
        if (isChild) return childPrice()

        return price
    }

    private fun childPrice(): Price {
        val discountedPrice = (price.value * CHILD_DISCOUNT_RATE).toInt()

        return Price.of(discountedPrice)
    }
}