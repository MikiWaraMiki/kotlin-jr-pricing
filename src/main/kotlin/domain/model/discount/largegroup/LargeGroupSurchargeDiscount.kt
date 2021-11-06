package domain.model.discount.largegroup

import domain.model.discount.DiscountName
import domain.model.discount.Discount
import domain.model.fare.Fare
import domain.model.shared.Passengers
import domain.model.shared.Price
import domain.model.surcharge.Surcharge

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