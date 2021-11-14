package jrpricing.demo.first.domain.model.station

import jrpricing.demo.first.domain.model.exception.DomainException
import jrpricing.demo.first.domain.model.exception.ErrorCode

/**
 * 駅
 */
enum class Station(private val label: String) {
    TOKYO("tokyo"),
    SHIN_OSAKA("shin_osaka"),
    HIMEJI("himeji");


    companion object {
        fun fromLabel(label: String): Station {
            val station = values().firstOrNull { it.label == label } ?:
                throw DomainException("存在しない駅です", ErrorCode.INVALID_INPUT)
            return station
        }
    }
}