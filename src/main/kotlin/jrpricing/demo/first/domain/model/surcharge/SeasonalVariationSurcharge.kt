package jrpricing.demo.first.domain.model.surcharge

import jrpricing.demo.first.domain.model.season.SeasonType
import jrpricing.demo.first.domain.model.shared.Price
import jrpricing.demo.first.domain.model.ticket.DepartureDate

/**
 * 季節変動特急料金
 */
class SeasonalVariationSurcharge private constructor(
    private val baseSurcharge: Surcharge,
    private val departureDate: DepartureDate
) {
    private fun calc(): Surcharge {
        if (SeasonType.isPeak(departureDate.date)) {
            val price = Price(baseSurcharge.price(false).value + SEASONAL_VARIATION_PRICE)
            return Surcharge(price)
        }

        if (SeasonType.isOffPeak(departureDate.date)) {
            val price = Price(baseSurcharge.price(false).value - SEASONAL_VARIATION_PRICE)
            return Surcharge(price)
        }

        return baseSurcharge
    }

    companion object {
        private const val SEASONAL_VARIATION_PRICE = 200

        fun calc(surcharge: Surcharge, departureDate: DepartureDate): Surcharge {
            return SeasonalVariationSurcharge(
                baseSurcharge = surcharge,
                departureDate = departureDate
            ).calc()
        }
    }
}