package jrpricing.demo.first.domain.model.fare

import jrpricing.demo.first.domain.model.shared.Price


/**
 * 運賃クラス
 */
class Fare(private val price: Price) {

    fun price(isChild: Boolean): Price {
        if (isChild) return childPrice()

        return price
    }

    private fun childPrice(): Price {
        val discountedPrice = (price.value * CHILD_DISCOUNT_RATE).toInt()

        return Price.of(discountedPrice)
    }

    companion object {
        private const val CHILD_DISCOUNT_RATE = 0.5
    }
}