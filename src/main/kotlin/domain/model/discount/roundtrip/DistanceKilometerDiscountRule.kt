package domain.model.discount.roundtrip

import domain.model.discount.DiscountRule
import domain.model.fare.Fare
import domain.model.shared.Price
import domain.model.ticket.Ticket

/**
 * 長距離移動割引適用ルール
 */
class DistanceKilometerDiscountRule(
    private val ticket: Ticket,
): DiscountRule {
    private val DISCOUNT_RATE = 0.1

    /**
     * 片道の移動距離に応じて割引が適用可能か確かめる
     */
    override fun can(): Boolean {
        if (ticket.isOneway()) return  false

        return ticket.isLongDistance()
    }


    /**
     * ルール適用後の割引料金
     */
    override fun price(fare: Fare): Fare {
       val basePrice = fare.price

        val discountedCalcResult = (basePrice.value * (1 - DISCOUNT_RATE)).toInt()

        val truncated = discountedCalcResult % 10

        return Fare(Price(truncated))
    }

}