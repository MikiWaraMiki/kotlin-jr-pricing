package jrpricing.demo.first.domain.model.train

import jrpricing.demo.first.domain.model.exception.DomainException
import jrpricing.demo.first.domain.model.exception.ErrorCode

/**
 * 席区分
 */
enum class SeatType(private val label: String) {
    NON_RESERVED("non_reserved"),
    RESERVED("reserved");

    fun isReserved(): Boolean {
        return this == RESERVED
    }

    companion object {
        fun fromLabel(label: String): SeatType {
            return values().firstOrNull { it.label == label } ?:
                throw DomainException("指定席もしくは自由席を選択してください", ErrorCode.INVALID_INPUT)
        }
    }
}