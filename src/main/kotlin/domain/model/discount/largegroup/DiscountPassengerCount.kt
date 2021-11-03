package domain.model.discount.largegroup

import domain.model.shared.Passengers
import kotlin.math.min

/**
 * 割引適用人数クラス
 */
class DiscountPassengerCount(
    private val passengers: Passengers
) {
    private val noChargePassengersNum = passengers.totalPassengers() / DISCOUNT_PER_NUM

    fun adultDiscountNumber(): Int {
        return min(passengers.adults, noChargePassengersNum)
    }

    fun childDiscountNumber(): Int {
        return min(passengers.childs, noChargePassengersNum - adultDiscountNumber())
    }

    companion object {
        private val DISCOUNT_PER_NUM = 50
    }
}