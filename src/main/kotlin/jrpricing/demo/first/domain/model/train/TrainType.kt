package jrpricing.demo.first.domain.model.train

import jrpricing.demo.first.domain.model.exception.DomainException
import jrpricing.demo.first.domain.model.exception.ErrorCode
import java.lang.IllegalArgumentException

/**
 * 列車区分
 */
enum class TrainType(private val label: String) {
    HIKARI("hikari"),
    NOZOMI("nozomi");

    fun isNozomi(): Boolean {
        return this == NOZOMI
    }

    companion object {
        fun fromLabel(label: String): TrainType {
            return values().firstOrNull { it.label == label } ?:
                throw DomainException("のぞみもしくはひかりを選択してください", ErrorCode.INVALID_INPUT)
        }
    }
}