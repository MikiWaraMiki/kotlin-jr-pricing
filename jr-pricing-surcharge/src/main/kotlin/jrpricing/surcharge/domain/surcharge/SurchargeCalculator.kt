package jrpricing.surcharge.domain.surcharge

import jrpricing.surcharge.domain.shared.DepartureMonthDay
import jrpricing.surcharge.domain.shared.SeatType

/**
 * 特急料金計算
 */
class SurchargeCalculator {
    fun calc(reserveSeatSurcharge: ReserveSeatSurcharge, departureMonthDay: DepartureMonthDay, seatType: SeatType): Surcharge {
        if (!seatType.isReserve()) {
            val nonReserveSeatSurcharge = NonReserveSeatSurcharge.from(reserveSeatSurcharge)
            return Surcharge(nonReserveSeatSurcharge.amount)
        }

        // NOTE: 指定席利用時には、乗車日に応じて料金変動が発生する
        val seasonalVariationAmount = SeasonVariationAmount(reserveSeatSurcharge)

        return Surcharge(seasonalVariationAmount.amount(departureMonthDay))
    }
}