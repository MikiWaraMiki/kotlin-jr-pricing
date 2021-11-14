package jrpricing.catalog.domain.model.route

import com.github.guepardoapps.kulid.ULID
import jrpricing.catalog.domain.exception.DomainException
import jrpricing.catalog.domain.exception.ErrorCode
import jrpricing.catalog.domain.model.station.StationId

/**
 * 経路ID
 */
class RouteId private constructor(
    val value: String
){
    init {
        if (value == "") {
            throw DomainException("経路IDが入力されていません", ErrorCode.INVALID_INPUT)
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
        fun create(): RouteId {
            return RouteId(
                ULID.random()
            )
        }

        fun reConstructor(routeId: String): RouteId {
            return RouteId(routeId)
        }
    }
}