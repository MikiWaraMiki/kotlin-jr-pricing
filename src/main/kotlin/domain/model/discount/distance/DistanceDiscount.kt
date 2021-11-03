package domain.model.discount.distance

import domain.model.discount.DiscountName
import domain.model.discount.Discount
import domain.model.discount.DiscountRate
import domain.model.fare.Fare
import domain.model.shared.Passengers
import domain.model.shared.Price
import kotlin.math.floor

/**
 * 距離割引クラス
 */
class DistanceDiscount private constructor(
    private val basePrice: Price
): Discount {
    override val discountName = DiscountName("長距離往復割引")

    fun discountedPrice(): Price {
        return Price(basePrice.value - afterDiscountedPrice().value)
    }

    override fun afterDiscountedPrice(): Price {
        val discount = floor(basePrice.value * (DISCOUNT_RATE.value / 100)).toInt()
        return Price.of(basePrice.value - discount)
    }

    companion object {
        private val DISCOUNT_RATE = DiscountRate.RATE_10

        fun of(fare: Fare, passengers: Passengers): DistanceDiscount {
            val adultTotal = fare.price(false).value * passengers.adults
            val childTotal = fare.price(true).value * passengers.childs

            return DistanceDiscount(Price(adultTotal + childTotal))
        }
    }
}