package jrpricing.catalog.domain.model.train

import jrpricing.catalog.domain.exception.DomainException

/**
 * 列車種別
 */
enum class TrainType(val value: String, val id: Int) {
    NOZOMI("のぞみ", 1),
    HIKARI("ひかり", 2);

    fun isNozomi(): Boolean {
        return this === NOZOMI
    }

    companion object {
        fun fromId(id: Int): TrainType {
            return values().firstOrNull { it.id == id }
                ?: throw DomainException("存在しない列車種別です")
        }
    }
}