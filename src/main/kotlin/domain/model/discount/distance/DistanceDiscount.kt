package domain.model.discount.distance

import domain.model.discount.DiscountPrice
import domain.model.discount.DiscountRate
import domain.model.shared.Price
import kotlin.math.floor

/**
 * 距離割引クラス
 */
class DistanceDiscount constructor(
    private val basePrice: Price
): DiscountPrice {

    override fun afterDiscountedPrice(): Price {
        val discount = floor(basePrice.value * (DISCOUNT_RATE.value / 100)).toInt()
        return Price.of(basePrice.value - discount)
    }

    companion object {
        private val DISCOUNT_RATE = DiscountRate.RATE_10
    }
}