package jrpricing.surcharge.domain.surcharge

import jrpricing.surcharge.domain.season.SeasonType
import jrpricing.surcharge.domain.shared.Amount
import jrpricing.surcharge.domain.shared.DepartureMonthDay

/**
 * 季節変動料金
 */
class SeasonVariationAmount(
    private val basicSurcharge: BasicSurcharge
) {

    fun amount(departureMonthDay: DepartureMonthDay): Amount {
        if (SeasonType.isPeak(departureMonthDay.value)) {
            return Amount.of(basicSurcharge.amount.value + PEAK_PLUS_PRICE)
        }

        if (SeasonType.isOffPeak(departureMonthDay.value)) {
            return Amount.of(basicSurcharge.amount.value - OFF_PEAK_MINUS_PRICE)
        }

        return basicSurcharge.amount
    }

    companion object {
        private const val OFF_PEAK_MINUS_PRICE = 200
        private const val PEAK_PLUS_PRICE = 200
    }
}