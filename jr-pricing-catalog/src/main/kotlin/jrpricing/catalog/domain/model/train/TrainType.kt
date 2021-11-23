package jrpricing.catalog.domain.model.train

import jrpricing.catalog.domain.exception.DomainException

enum class TrainType(val trainName: String, val id: Int) {
    NOZOMI("のぞみ", 1),
    HIKARI("ひかり", 2);

    fun isNozomi(): Boolean {
        return this == NOZOMI
    }

    companion object {
        fun fromId(id: Int): TrainType {
            return values().firstOrNull { it.id == id }
                ?: throw DomainException("存在しない列車種別です")
        }
    }
}