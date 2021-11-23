package jrpricing.surcharge.domain.surcharge

import jrpricing.surcharge.domain.season.SeasonType
import jrpricing.surcharge.domain.shared.Amount
import jrpricing.surcharge.domain.shared.DepartureMonthDay

/**
 * 季節変動料金
 */
class SeasonVariationAmount private constructor(
    val amount: Amount
) {
    companion object {
        private const val OFF_PEAK_MINUS_PRICE = 200
        private const val PEAK_PLUS_PRICE = 200

        fun from(reserveSeatSurcharge: ReserveSeatSurcharge, departureMonthDay: DepartureMonthDay): SeasonVariationAmount {
            if (SeasonType.isPeak(departureMonthDay.value)) {
                val amount = Amount.of(reserveSeatSurcharge.amount.value + PEAK_PLUS_PRICE)
                return SeasonVariationAmount(amount)
            }

            if (SeasonType.isOffPeak(departureMonthDay.value)) {
                val amount = Amount.of(reserveSeatSurcharge.amount.value - OFF_PEAK_MINUS_PRICE)
                return SeasonVariationAmount(amount)
            }

            return SeasonVariationAmount(reserveSeatSurcharge.amount)

        }
    }
}