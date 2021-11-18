package jrpricing.catalog.usecase.basicfare

import jrpricing.catalog.domain.model.fare.BasicFare
import jrpricing.catalog.domain.model.fare.BasicFareRepository
import jrpricing.catalog.domain.model.route.Route
import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.route.RouteRepository
import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.usecase.exception.AssertionFailException
import jrpricing.catalog.usecase.exception.ErrorCode
import org.springframework.stereotype.Service

/**
 * 指定された駅間の運賃料金を返すユースケースクラス
 */
@Service
class FindBasicFareByRouteIdUsecase(
    private val routeRepository: RouteRepository,
    private val basicFareRepository: BasicFareRepository
) {
    fun execute(departureStationId: StationId, arrivalStationId: StationId): BasicFare {
        val route = routeRepository.findByDepartureAndArrivalStation(departureStationId, arrivalStationId)
            ?: throw AssertionFailException("指定した駅間の経路は存在しません", ErrorCode.NOTFOUND_ASSERTION)

        val basicFare = basicFareRepository.findByRouteId(route.routeId)
            ?: throw AssertionFailException("指定した経路の運賃は存在しません", ErrorCode.NOTFOUND_ASSERTION)

        return basicFare
    }
}