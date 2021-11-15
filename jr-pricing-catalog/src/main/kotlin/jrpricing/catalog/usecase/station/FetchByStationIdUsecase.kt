package jrpricing.catalog.usecase.station

import jrpricing.catalog.domain.model.station.Station
import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.domain.model.station.StationRepository
import jrpricing.catalog.usecase.exception.AssertionFailException
import jrpricing.catalog.usecase.exception.ErrorCode
import org.springframework.stereotype.Service

/**
 * 駅IDによる駅検索ユースケース
 */
@Service
class FetchByStationIdUsecase(
    private val stationRepository: StationRepository
) {
    fun execute(stationId: StationId): Station {
        return stationRepository.findById(stationId)
            ?: throw AssertionFailException("指定した駅は存在しません", ErrorCode.NOTFOUND_ASSERTION)
    }
}