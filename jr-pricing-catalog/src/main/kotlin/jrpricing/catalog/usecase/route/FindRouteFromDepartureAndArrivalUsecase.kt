package jrpricing.catalog.usecase.route

import jrpricing.catalog.domain.model.route.RouteDistance
import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.route.RouteRepository
import jrpricing.catalog.domain.model.station.Station
import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.domain.repository.station.StationRepository
import jrpricing.catalog.usecase.exception.AssertionFailException
import jrpricing.catalog.usecase.exception.ErrorCode
import org.springframework.stereotype.Service
import java.lang.RuntimeException

data class FindRouteFromDepartureAndArrivalUsecaseResultDto(
    val routeId: RouteId,
    val distance: RouteDistance,
    val departureStation: Station,
    val arrivalStation: Station
)

/**
 * 出発駅と到着駅から経路の検索を行うユースケース
 */
@Service
class FindRouteFromDepartureAndArrivalUsecase(
    private val routeRepository: RouteRepository,
    private val stationRepository: StationRepository
) {
    fun execute(
        departureStationId: StationId,
        arrivalStationId: StationId
    ): FindRouteFromDepartureAndArrivalUsecaseResultDto {
        val route = routeRepository.findByDepartureAndArrivalStation(
            departureStationId,
            arrivalStationId
        ) ?: throw AssertionFailException("存在しない経路です", ErrorCode.NOTFOUND_ASSERTION)

        val departureStation = findStation(departureStationId)
        val arrivalStation = findStation(arrivalStationId)

        return FindRouteFromDepartureAndArrivalUsecaseResultDto(
            route.routeId,
            route.distance,
            departureStation,
            arrivalStation
        )
    }

    private fun findStation(stationId: StationId): Station {
        // NOTE: 外部キーを貼っているので基本的には発生しない想定
        return stationRepository.findById(stationId) ?: throw RuntimeException(
            "経路に登録されている駅が存在しませんでした"
        )
    }

}