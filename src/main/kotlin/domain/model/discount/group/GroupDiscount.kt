package domain.model.discount.group

import domain.model.discount.DiscountPrice
import domain.model.discount.DiscountRate
import domain.model.shared.Price
import domain.model.ticket.DepartureDate
import kotlin.math.floor

/**
 * 通常団体割引クラス
 */
class GroupDiscount private constructor(
    private val basePrice: Price,
    private val discountRate: DiscountRate
): DiscountPrice {

    override fun afterDiscountedPrice(): Price {
        val discount = floor(basePrice.value * (discountRate.value / 100)).toInt()
        return Price.of(basePrice.value - discount)
    }

    companion object {
        fun fromDepartureDate(basePrice: Price, departureDate: DepartureDate): GroupDiscount {
            val rate = GroupDiscountRateCategory.rateFromDate(departureDate.date)
            return GroupDiscount(basePrice, rate)
        }
    }
}