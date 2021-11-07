package domain.model.surcharge

import domain.model.shared.Price

/**
 * 特急料金クラス
 */
class Surcharge(
    private val price: Price
) {

    fun price(isChild: Boolean): Price {
        if (isChild) return childPrice()

        return price
    }

    // TODO: fareと重複があるので見直す
    private fun childPrice(): Price {
        val discountedPrice = (price.value * CHILD_DISCOUNT_RATE).toInt()

        return Price.of(discountedPrice)
    }

    companion object {
        private const val CHILD_DISCOUNT_RATE = 0.5
    }
}