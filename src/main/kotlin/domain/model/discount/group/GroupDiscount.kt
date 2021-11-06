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
    private val fare: Fare,
    private val discountRate: DiscountRate
): Discount {
    override val discountName = DiscountName("通常団体（乗車人数が8人以上30人以下の場合に適用）")

    val afterDiscountedFare = afterDiscountFare()

    private fun afterDiscountFare(): AfterDiscountedFare {
        val discount = floor(fare.price(false).value * discountRate.few()).toInt()

        val discountResult = fare.price(false).value - discount

        return AfterDiscountedFare.fromPrice(Price.of(discountResult))
    }

    companion object {
        fun fromDepartureDate(fare: Fare, departureDate: DepartureDate): GroupDiscount {
            val rate = GroupDiscountRateCategory.rateFromDate(departureDate.date)
            return GroupDiscount(fare, rate)
        }
    }
}