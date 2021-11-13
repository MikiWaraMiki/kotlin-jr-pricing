package jrpricing.catalog.domain.model.station

import com.github.guepardoapps.kulid.ULID
import jrpricing.demo.first.domain.model.exception.DomainException
import jrpricing.demo.first.domain.model.exception.ErrorCode

/**
 * 駅ID
 */
class StationId private constructor(
    val value: String
) {

    init {
        if (value == "") {
            throw DomainException("駅IDが入力されていません", ErrorCode.INVALID_INPUT)
        }
    }

    override fun hashCode(): Int {
        return value.hashCode() * 31
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StationId

        if (value != other.value) return false

        return true
    }

    companion object {
        fun create(): StationId {
            return StationId(
                ULID.random()
            )
        }

        fun reConstructor(stationIdString: String): StationId {
            return StationId(stationIdString)
        }
    }
}