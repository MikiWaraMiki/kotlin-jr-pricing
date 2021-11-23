package jrpricing.surcharge.domain.surcharge

import jrpricing.surcharge.domain.shared.Amount

/**
 * 自由席特急料金
 */
class NonReserveSeatSurcharge private constructor(
    val amount: Amount
){

    companion object {
        private const val NON_RESERVE_SEAT_MINUS_PRICE = 530

        fun from(reserveSeatSurcharge: ReserveSeatSurcharge): NonReserveSeatSurcharge {
            val resultAmount = Amount.of(reserveSeatSurcharge.amount.value - NON_RESERVE_SEAT_MINUS_PRICE)

            return NonReserveSeatSurcharge(resultAmount)
        }
    }
}