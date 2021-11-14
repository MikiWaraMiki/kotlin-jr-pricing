package jrpricing.demo.first.domain.model.shared

import jrpricing.demo.first.domain.model.exception.DomainException
import jrpricing.demo.first.domain.model.exception.ErrorCode

/**
 * 搭乗員数クラス
 */
class Passengers(
    adultPassengerCount: Int,
    childPassengerCount: Int
) {
    val adults: Int
    val childs: Int

    init {
        val totalPassengers = adultPassengerCount + childPassengerCount

        if (totalPassengers < 1) throw DomainException("乗車人数が０人です。", ErrorCode.INVALID_INPUT)

        adults = adultPassengerCount
        childs = childPassengerCount
    }

    fun totalPassengers(): Int {
        return adults + childs
    }
}