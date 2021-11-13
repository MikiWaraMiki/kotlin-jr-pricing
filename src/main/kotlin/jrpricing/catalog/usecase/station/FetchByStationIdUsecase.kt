package jrpricing.catalog.usecase.station

import jrpricing.catalog.domain.model.station.StationId
import jrpricing.catalog.domain.repository.station.StationRepository

/**
 * 駅IDによる駅検索ユースケース
 */
class FetchByStationIdUsecase(
    private val stationRepository: StationRepository
) {
    fun execute(stationId: StationId) {
        val station = stationRepository.findById(stationId)

        if (station == null) {

        }
    }
}