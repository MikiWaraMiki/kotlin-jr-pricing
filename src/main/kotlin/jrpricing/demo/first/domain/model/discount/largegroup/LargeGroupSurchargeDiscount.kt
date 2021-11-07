package jrpricing.demo.first.domain.model.discount.largegroup

import jrpricing.demo.first.domain.model.discount.DiscountName
import jrpricing.demo.first.domain.model.discount.Discount
import jrpricing.demo.first.domain.model.shared.Passengers
import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.surcharge.Surcharge

/**
 * 特別団体割引クラス
 */
class LargeGroupSurchargeDiscount(
    private val surcharge: Surcharge,
    private val passengers: Passengers
    ): Discount {
    override val discountName = DiscountName("団体割引（乗員人数が31人以上の場合に適用）")

    private val freePassengerCount = FreePassengerCount(passengers)

    override fun afterDiscounted(): Price {
        val adults = passengers.adults - freePassengerCount.adultDiscountNumber()
        val childs = passengers.childs - freePassengerCount.childDiscountNumber()

        val adultResult = surcharge.price(false).value * adults
        val childResult = surcharge.price(true).value * childs

        return Price.of(adultResult + childResult)
    }
}