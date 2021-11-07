package jrpricing.demo.first.domain.model.discount.largegroup

import jrpricing.demo.first.domain.model.discount.Discount
import jrpricing.demo.first.domain.model.discount.DiscountName
import jrpricing.demo.first.domain.model.fare.Fare
import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.shared.Price

/**
 * 団体運賃割引
 */
class LargeGroupFareDiscount(
    private val fare: Fare,
    private val passengers: Passengers
): Discount {
    override val discountName = DiscountName("団体割引（乗員人数が31人以上の場合に適用）")

    private val freePassengerCount = FreePassengerCount(passengers)

    override fun afterDiscounted(): Price {
        val adults = passengers.adults - freePassengerCount.adultDiscountNumber()
        val childs = passengers.childs - freePassengerCount.childDiscountNumber()

        val adultResult = fare.price(false).value * adults
        val childResult = fare.price(true).value * childs

        return Price.of(adultResult + childResult)
    }
}