package jrpricing.surcharge.domain.surcharge

import jrpricing.surcharge.domain.shared.Amount
import jrpricing.surcharge.domain.shared.DepartureMonthDay

/**
 * 指定席特急料金
 */
class ReserveSeatSurcharge(
    val amount: Amount
) {
    fun addSeasonVariationAmount(departureMonthDay: DepartureMonthDay): Amount {
        val result = SeasonVariationAmount.from(this, departureMonthDay)
        return result.amount
    }
}