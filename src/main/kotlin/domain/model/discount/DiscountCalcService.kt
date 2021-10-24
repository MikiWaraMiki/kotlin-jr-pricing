package domain.model.discount

import domain.model.discount.rule.DiscountRule
import domain.model.discount.rule.DistanceKilometerDiscountRule
import domain.model.discount.rule.GroupDiscountRule
import domain.model.shared.Passengers
import domain.model.shared.Price
import domain.model.ticket.Ticket

/**
 * 割引額計算サービス
 * @param ticket 乗車券
 * @param passengers 乗車人数
 */
class DiscountCalcService(
    private val basePrice: Price,
    private val ticket: Ticket,
    private val passengers: Passengers
) {
    private val discountRuleList = listOf<DiscountRule>(
        DistanceKilometerDiscountRule(ticket.ticketType, ticket.route),
        GroupDiscountRule(passengers, ticket.departureDate)
    )

    /*
    fun calc(): List<Discount> {
        val enabledDiscountRuleList = discountRuleList.filter {
            it.can()
        }

        return enabledDiscountRuleList.map {
            Discount(it.rate(), basePrice)
        }
    }
    */
}