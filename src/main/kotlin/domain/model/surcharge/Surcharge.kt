package domain.model.surcharge

import domain.model.shared.Price

/**
 * 特急料金クラス
 */
class Surcharge(
    aPrice: Price
) {
    private val CHILD_DISCOUNT_RATE = 0.5
    var price = aPrice
        private set

    fun price(isChild: Boolean): Price {
        if (isChild) return childPrice()

        return price
    }

    fun addPrice(added: Price) {
        price = Price(price.value + added.value)
    }

    fun discountPrice(discounted: Price) {
        price = Price(price.value - discounted.value)
    }

    // TODO: fareと重複があるので見直す
    private fun childPrice(): Price {
        val discountedPrice = (price.value * CHILD_DISCOUNT_RATE).toInt()

        // 1円単位・5円単位の端数は切り捨て
        val truncated = discountedPrice % 10
        return Price(discountedPrice - truncated)
    }
}