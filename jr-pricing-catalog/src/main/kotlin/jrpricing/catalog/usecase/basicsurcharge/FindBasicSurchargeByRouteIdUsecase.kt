package jrpricing.catalog.usecase.basicsurcharge

import jrpricing.catalog.domain.model.route.RouteRepository
import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.domain.model.surcharge.BasicSurcharge
import jrpricing.catalog.domain.model.surcharge.BasicSurchargeRepository
import jrpricing.catalog.usecase.exception.AssertionFailException
import jrpricing.catalog.usecase.exception.ErrorCode
import org.springframework.stereotype.Service

/**
 * 指定された駅間の特急料金を返すユースケース
 */
@Service
class FindBasicSurchargeByRouteIdUsecase(
    private val routeRepository: RouteRepository,
    private val basicSurchargeRepository: BasicSurchargeRepository
) {
    fun execute(departureStationId: StationId, arrivalStationId: StationId): BasicSurcharge {
        val route = routeRepository.findByDepartureAndArrivalStation(departureStationId, arrivalStationId)
            ?: throw AssertionFailException("指定した駅間の経路は存在しません", ErrorCode.NOTFOUND_ASSERTION)

        val basicSurcharge = basicSurchargeRepository.findByRouteId(route.routeId)
            ?: throw AssertionFailException("指定した経路の特急料金は存在しません", ErrorCode.NOTFOUND_ASSERTION)

        return basicSurcharge
    }
}