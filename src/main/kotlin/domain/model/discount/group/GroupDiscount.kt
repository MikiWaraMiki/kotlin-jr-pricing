package domain.model.discount.group

import domain.model.discount.*
import domain.model.fare.Fare
import domain.model.shared.Price
import domain.model.ticket.DepartureDate
import kotlin.math.floor

/**
 * 通常団体割引クラス
 */
class GroupDiscount(
    override val fare: Fare,
    private val discountRate: DiscountRate
): FareDiscount {
    override val discountName = DiscountName("通常団体（乗車人数が8人以上30人以下の場合に適用）")

    override fun afterDiscountedPrice(): Price {
        return Price(8000)
    }

    override fun afterDiscountFare(): Fare {
        val discount = floor(fare.price(false).value * discountRate.few()).toInt()

        val discountResult = fare.price(false).value - discount

        return Fare(Price.of(discountResult))
    }

    companion object {
        fun fromDepartureDate(fare: Fare, departureDate: DepartureDate): GroupDiscount {
            val rate = GroupDiscountRateCategory.rateFromDate(departureDate.date)
            return GroupDiscount(fare, rate)
        }
    }
}