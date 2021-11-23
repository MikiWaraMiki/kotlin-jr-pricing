package jrpricing.surcharge.domain.shared

import jrpricing.surcharge.domain.exception.DomainException

enum class TrainType(val typeName: String) {
    HIKARI("hikari"),
    NOZOMI("nozomi");

    companion object {
        fun fromTypeName(typeName: String): TrainType {
            return values().firstOrNull() { it.typeName == typeName }
                ?: throw DomainException("存在しない列車種別です")
        }
    }
}