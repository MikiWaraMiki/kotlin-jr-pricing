package jrpricing.demo.first.domain.model.discount.distance

import jrpricing.demo.first.domain.model.discount.DiscountName
import jrpricing.demo.first.domain.model.discount.Discount
import jrpricing.demo.first.domain.model.discount.DiscountRate
import jrpricing.demo.first.domain.model.fare.Fare
import jrpricing.demo.first.domain.model.shared.Price
import kotlin.math.floor

/**
 * 距離割引クラス
 */
class DistanceDiscount(
    private val fare: Fare
): Discount {
    override val discountName = DiscountName("長距離往復割引")

    override fun afterDiscounted(): Price {
        val discount = floor(fare.price(false).value * (DISCOUNT_RATE.value / 100)).toInt()

        return Price.of(fare.price(false).value - discount)
    }

    companion object {
        private val DISCOUNT_RATE = DiscountRate.RATE_10
    }
}