package jrpricing.catalog.domain.repository.station

import jrpricing.catalog.domain.model.station.Station
import jrpricing.catalog.domain.model.station.StationId

/**
 * 駅リポジトリ
 */
interface StationRepository {
    fun findById(stationId: StationId): Station?
}