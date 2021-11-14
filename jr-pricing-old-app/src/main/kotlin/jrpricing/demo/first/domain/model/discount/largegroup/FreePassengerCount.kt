package jrpricing.demo.first.domain.model.discount.largegroup

import jrpricing.demo.first.domain.model.shared.Passengers
import kotlin.math.min

/**
 * 割引適用人数クラス
 */
class FreePassengerCount(
    private val passengers: Passengers
) {
    private val noChargePassengersNum = passengers.totalPassengers() / DISCOUNT_PER_NUMBER

    fun adultDiscountNumber(): Int {
        if (passengers.adults < 1) return 0

        if (!isOverThan()) return 1

        return min(passengers.adults, noChargePassengersNum)
    }

    fun childDiscountNumber(): Int {
        if (passengers.childs < 1) return 0

        if (!isOverThan() && adultDiscountNumber() < 1) return 1

        // NOTE: 人数が50人未満で大人の人数がすでに1人割引されている場合
        if (!isOverThan()) return 0

        return min(passengers.childs, noChargePassengersNum - adultDiscountNumber())
    }

    private fun isOverThan(): Boolean {
        return passengers.totalPassengers() >= ENABLE_DISCOUNT_PER_NUMBER_LIMIT
    }

    companion object {
        private val ENABLE_DISCOUNT_PER_NUMBER_LIMIT = 51
        private val DISCOUNT_PER_NUMBER = 50
    }
}