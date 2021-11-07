package domain.model.surcharge

import domain.model.shared.Price
import domain.model.ticket.DepartureDate
import domain.model.train.SeatType

/**
 * 座席種別特急料金変動
 */
class SeatTypeSurcharge private constructor(
    private val baseSurcharge: Surcharge,
    private val departureDate: DepartureDate
) {

    private fun reservedSurcharge(): Surcharge {
        return SeasonalVariationSurcharge.calc(baseSurcharge, departureDate)
    }

    private fun nonReservedSurcharge(): Surcharge {
        val discounted = baseSurcharge.price(false).value - 530
        val discountedPrice = Price(discounted)

        return Surcharge(discountedPrice)
    }

    companion object {
        private const val NON_RESERVED_DISCOUNT_PRICE = 530

        fun calc(baseSurcharge: Surcharge, seatType: SeatType, departureDate: DepartureDate): Surcharge {
            val seatTypeSurcharge = SeatTypeSurcharge(baseSurcharge, departureDate)

            return if (seatType.isReserved()) {
                seatTypeSurcharge.reservedSurcharge()
            } else {
                seatTypeSurcharge.nonReservedSurcharge()
            }
        }
    }
}