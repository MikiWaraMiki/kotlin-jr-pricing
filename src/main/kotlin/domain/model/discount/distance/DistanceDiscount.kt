package domain.model.discount.distance

import domain.model.discount.AfterDiscountedFare
import domain.model.discount.DiscountName
import domain.model.discount.Discount
import domain.model.discount.DiscountRate
import domain.model.fare.Fare
import domain.model.shared.Price
import kotlin.math.floor

/**
 * 距離割引クラス
 */
class DistanceDiscount(
    private val fare: Fare
): Discount {
    override val discountName = DiscountName("長距離往復割引")

    fun afterDiscountFare(): AfterDiscountedFare {
        val discount = floor(fare.price(false).value * (DISCOUNT_RATE.value / 100)).toInt()
        val discountedPrice = Price.of(fare.price(false).value - discount)

        return AfterDiscountedFare.fromPrice(discountedPrice)
    }

    companion object {
        private val DISCOUNT_RATE = DiscountRate.RATE_10
    }
}