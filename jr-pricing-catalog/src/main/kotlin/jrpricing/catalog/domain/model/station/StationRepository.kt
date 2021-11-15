package jrpricing.catalog.domain.model.station

import jrpricing.catalog.domain.model.station.Station
import jrpricing.catalog.domain.model.station.StationId

/**
 * 駅リポジトリ
 */
interface StationRepository {
    fun findById(stationId: StationId): Station?
    fun insert(station: Station)
}