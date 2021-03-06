package jrpricing.demo.first.domain.model.discount.group

import jrpricing.demo.first.domain.model.discount.*
import jrpricing.demo.first.domain.model.fare.Fare
import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.ticket.DepartureDate
import kotlin.math.floor

/**
 * 通常団体割引クラス
 */
class GroupDiscount(
    private val fare: Fare,
    private val discountRate: DiscountRate
): Discount {
    override val discountName = DiscountName("通常団体（乗車人数が8人以上30人以下の場合に適用）")

    override fun afterDiscounted(): Price {
        val discount = floor(fare.price(false).value * discountRate.few()).toInt()

        val discountResult = fare.price(false).value - discount

        return Price.of(discountResult)
    }

    companion object {
        fun fromDepartureDate(fare: Fare, departureDate: DepartureDate): GroupDiscount {
            val rate = GroupDiscountRateCategory.rateFromDate(departureDate.date)
            return GroupDiscount(fare, rate)
        }
    }
}