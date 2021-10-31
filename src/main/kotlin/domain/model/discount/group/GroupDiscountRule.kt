package domain.model.discount.group

import domain.model.discount.rate.DiscountRate
import domain.model.discount.rule.DiscountRule
import domain.model.shared.Passengers
import domain.model.ticket.DepartureDate

/**
 * 8人以上の団体割引適用ルール
 */
class GroupDiscountRule(
    private val passengers: Passengers,
    private val departureDate: DepartureDate
): DiscountRule {
    private val GREETER_THEN_PASSENGER_NUM = 8

    override fun can(): Boolean {
        return passengers.totalPassengers() >= GREETER_THEN_PASSENGER_NUM
    }
}